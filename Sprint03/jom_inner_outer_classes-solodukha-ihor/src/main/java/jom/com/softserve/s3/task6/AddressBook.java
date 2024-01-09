package jom.com.softserve.s3.task6;
// Write your code her
import java.util.Arrays;
import java.util.Iterator;
import java.util.Comparator;
import java.util.NoSuchElementException;

public class AddressBook implements Iterable {
    private NameAddressPair[] addressBook;
    private int counter = 0;

    public AddressBook(int capacity) {
        addressBook = new NameAddressPair[capacity];
    }

    public boolean create(String firstName, String lastName, String address) {
        for(NameAddressPair ex : addressBook) {
            if(ex != null && ex.person.equals(new NameAddressPair.Person(firstName, lastName))) {
                return false;
            }
        }
        if(counter == addressBook.length) {
            addressBook = Arrays.copyOf(addressBook, addressBook.length * 2);
        }
        addressBook[counter] = new NameAddressPair(new NameAddressPair.Person(firstName, lastName), address);
        counter++;
        return true;
    }

    public String read(String firstName, String lastName) {
        for(NameAddressPair ex : addressBook) {
            if(ex != null && ex.person.equals(new NameAddressPair.Person(firstName, lastName))) {
                return ex.address;
            }
        }
        return null;
    }
    public boolean update(String firstName, String lastName, String address) {
        for(NameAddressPair ex : addressBook) {
            if(ex != null && ex.person.equals(new NameAddressPair.Person(firstName, lastName))) {
                ex.address = address;
                return true;
            }
        }
        return false;
    }
    public boolean delete (String firstName, String lastName) {
        for (int i = 0; i < counter; i++) {
            if (addressBook[i] != null && addressBook[i].person.equals(new NameAddressPair.Person(firstName, lastName))) {
                for (int j = i; j < counter - 1; j++) {
                    addressBook[j] = addressBook[j + 1];
                }
                addressBook[counter - 1] = null; // Обнуляем последний элемент
                counter--; // Уменьшаем счетчик
                return true;
            }
        }
        return false;
    }

    public int size() {
        return this.counter;
    }

    public void sortedBy(SortOrder order) {
        Arrays.sort(addressBook, 0, counter, new Comparator<NameAddressPair>() {
            @Override
            public int compare(NameAddressPair pair1, NameAddressPair pair2) {
                int firstNameComparison = pair1.person.firstName.compareTo(pair2.person.firstName);
                if (firstNameComparison == 0) {
                    return (order == SortOrder.ASC) ? pair1.person.lastName.compareTo(pair2.person.lastName)
                            : pair2.person.lastName.compareTo(pair1.person.lastName);
                }
                return (order == SortOrder.ASC) ? firstNameComparison
                        : -firstNameComparison;
            }
        });
    }

    public Iterator iterator() {
        return new AddressBookIterator();
    }

    private class AddressBookIterator implements Iterator<String> {
        private int counter = 0; // Переменная для отслеживания текущей позиции в итераторе

        @Override
        public boolean hasNext() {
            while (counter < AddressBook.this.counter && addressBook[counter] == null) {
                counter++; // Пропускаем null элементы
            }
            return counter < AddressBook.this.counter;
        }

        @Override
        public String next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            return addressBook[counter++].toString();
        }
    }

    private static class NameAddressPair {
        private final Person person;
        private String address;
        private NameAddressPair(Person person, String address) {
            this.person = person;
            this.address = address;

        }

        @Override
        public String toString() {
            return "First name: " + person.firstName +
                    ", Last name: " + person.lastName +
                    ", Address: " + address;
        }


        private static class Person {
            private String firstName;
            private String lastName;

            private Person(String firstName, String lastName) {
                this.firstName = firstName;
                this.lastName = lastName;
            }

            public boolean equals(Object object) {
                if (this == object) return true;
                if (object == null || getClass() != object.getClass()) return false;
                Person person = (Person) object;
                return (firstName != null ? firstName.equals(person.firstName) : person.firstName == null)
                        && (lastName != null ? lastName.equals(person.lastName) : person.lastName == null);
            }
        }
    }
}
// Write your code here

enum SortOrder {
    ASC,
    DESC;
}