import org.h2.Driver;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class MyUtils {
	private Connection connection;
	private Statement statement;
	private String schemaName;

	public Connection createConnection() throws SQLException {
		DriverManager.registerDriver(new Driver());
		connection = DriverManager.getConnection("jdbc:h2:mem:test", "", "");
		return connection;
	}
	public void closeConnection() throws SQLException {
		connection.close();
		// code
	}
	public Statement createStatement() throws SQLException {
		statement = connection.createStatement();
		return statement;

		// code
	}
	public void closeStatement() throws SQLException {
		statement.close();
		// code
	}
	public void createSchema(String schemaName) throws SQLException {
		this.schemaName = schemaName;
		statement.execute("create schema " + schemaName + ";");
		// code
	}
	public void dropSchema() throws SQLException {
		statement.execute("drop schema " + schemaName + ";");
		// code
	}
	public void useSchema() throws SQLException {
		statement.execute("set schema " + schemaName + ";");
		// code
	}
	public void createTableRoles() throws SQLException {
		statement.executeUpdate("create table Roles (id int NOT NULL AUTO_INCREMENT, roleName varchar(50) NOT NULL, primary key (id));");
	}
	public void createTableDirections() throws SQLException {
		statement.executeUpdate("create table Directions (id int NOT NULL AUTO_INCREMENT, directionName varchar(50) NOT NULL, primary key (id));");
		// code
	}
	public void createTableProjects() throws SQLException {
		statement.executeUpdate("create table Projects (id int NOT NULL AUTO_INCREMENT, directionId int NOT NULL" +
				", projectName varchar(50) NOT NULL, primary key (id), foreign key (directionId) references Directions(id));");
		// code
	}
	public void createTableEmployee() throws SQLException {
		statement.executeUpdate("create table Employee(id int NOT NULL AUTO_INCREMENT, roleId int NOT NULL, projectId int NOT NULL" +
				", firstName varchar(50) NOT NULL, primary key (id), foreign key (roleId) references Roles(id), foreign key (projectId) references Projects(id));");
		// code
	}
	public void dropTable(String tableName) throws SQLException {
		String sql = "DROP TABLE " + tableName;
		statement.executeUpdate(sql);
		// code
	}
	public void insertTableRoles(String roleName) throws SQLException {
		String sql = "INSERT INTO Roles(roleName) VALUES ('" + roleName + "');";
		statement.executeUpdate(sql);
	}
	public void insertTableDirections(String directionName) throws SQLException {
		String sql = "INSERT INTO Directions (directionName) VALUES ('" + directionName + "')";
		statement.executeUpdate(sql);
	}
	public void insertTableProjects(String projectName, String directionName) throws SQLException {
		String sqlFindDirectionId = "SELECT id FROM Directions WHERE directionName = ?";
		try (PreparedStatement preparedStatement = connection.prepareStatement(sqlFindDirectionId)) {
			preparedStatement.setString(1, directionName);
			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				if (resultSet.next()) {
					int directionId = resultSet.getInt("id");

					String sqlInsertProject = "INSERT INTO Projects (projectName, directionId) VALUES (?, ?)";
					try (PreparedStatement preparedStatementInsert = connection.prepareStatement(sqlInsertProject)) {
						preparedStatementInsert.setString(1, projectName);
						preparedStatementInsert.setInt(2, directionId);
						preparedStatementInsert.executeUpdate();
					}
				}
			}
		}
	}
	public void insertTableEmployee(String firstName, String roleName, String projectName) throws SQLException {
		int roleId = getRoleId(roleName);
		int projectId = getProjectId(projectName);

		String sqlInsertEmployee = "INSERT INTO Employee (firstName, roleId, projectId) VALUES (?, ?, ?)";
		try (PreparedStatement preparedStatementInsert = connection.prepareStatement(sqlInsertEmployee)) {
			preparedStatementInsert.setString(1, firstName);
			preparedStatementInsert.setInt(2, roleId);
			preparedStatementInsert.setInt(3, projectId);
			preparedStatementInsert.executeUpdate();
		}
	}
	public int getRoleId(String roleName) throws SQLException {
		String sql = "SELECT id FROM Roles WHERE roleName = ?";
		try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
			preparedStatement.setString(1, roleName);
			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				if (resultSet.next()) {
					return resultSet.getInt("id");
				}
			}
		}
		return 0;
	}
	public int getDirectionId(String directionName) throws SQLException {
		String sql = "SELECT id FROM Directions WHERE directionName = ?";
		try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
			preparedStatement.setString(1, directionName);
			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				if (resultSet.next()) {
					return resultSet.getInt("id");
				}
			}
		}
		return 0;
	}
	public int getProjectId(String projectName) throws SQLException {
		String sql = "SELECT id FROM Projects WHERE projectName = ?";
		try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
			preparedStatement.setString(1, projectName);
			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				if (resultSet.next()) {
					return resultSet.getInt("id");
				}
			}
		}
		return 0;
	}
	public int getEmployeeId(String firstName) throws SQLException {
		String sql = "SELECT id FROM Employee WHERE firstName = ?";
		try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
			preparedStatement.setString(1, firstName);
			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				if (resultSet.next()) {
					return resultSet.getInt("id");
				}
			}
		}
		return 0;
	}
	public List<String> getAllRoles() throws SQLException {
		ResultSet resultSet = statement.executeQuery("SELECT roleName FROM Roles;");
		List<String> result = new ArrayList<>();

		while (resultSet.next()) {
			String roleName = resultSet.getString("roleName");
			result.add(roleName);
		}

		resultSet.close();
		return result;
	}
	public List<String> getAllDirestion() throws SQLException {
		ResultSet resultSet = statement.executeQuery("SELECT directionName FROM Directions;");
		List<String> result = new ArrayList<>();

		while (resultSet.next()) {
			String roleName = resultSet.getString("directionName");
			result.add(roleName);
		}

		resultSet.close();
		return result;
	}
	public List<String> getAllProjects() throws SQLException {
		ResultSet resultSet = statement.executeQuery("SELECT projectName FROM Projects;");
		List<String> result = new ArrayList<>();

		while (resultSet.next()) {
			String roleName = resultSet.getString("projectName");
			result.add(roleName);
		}

		resultSet.close();
		return result;
	}
	public List<String> getAllEmployee() throws SQLException {
		ResultSet resultSet = statement.executeQuery("SELECT firstName FROM Employee;");
		List<String> result = new ArrayList<>();

		while (resultSet.next()) {
			String roleName = resultSet.getString("firstName");
			result.add(roleName);
		}

		resultSet.close();
		return result;
	}
	public List<String> getAllDevelopers() throws SQLException {
		List<String> developers = new ArrayList<>();
		String sql = "SELECT E.firstName FROM Employee E JOIN Roles R ON E.roleId = R.id WHERE R.roleName = 'Developer'";
		try (ResultSet resultSet = statement.executeQuery(sql)) {
			while (resultSet.next()) {
				developers.add(resultSet.getString("firstName"));
			}
		}
		return developers;
	}
	public List<String> getAllJavaProjects() throws SQLException {
		List<String> javaProjects = new ArrayList<>();
		String sql = "SELECT P.projectName " +
				"FROM Projects P " +
				"JOIN Directions D ON P.directionId = D.id " +
				"WHERE D.directionName = 'Java'";
		try (Statement stmt = connection.createStatement();
			 ResultSet resultSet = stmt.executeQuery(sql)) {
			while (resultSet.next()) {
				javaProjects.add(resultSet.getString("projectName"));
			}
		}
		return javaProjects;
	}
	public List<String> getAllJavaDevelopers() throws SQLException {
		List<String> javaDevelopers = new ArrayList<>();
		String sql = "SELECT DISTINCT E.firstName " +
				"FROM Employee E " +
				"JOIN Roles R ON E.roleId = R.id " +
				"JOIN Projects P ON E.projectId = P.id " +
				"JOIN Directions D ON P.directionId = D.id " +
				"WHERE R.roleName = 'Developer' AND D.directionName = 'Java'";
		try (Statement stmt = connection.createStatement();
			 ResultSet resultSet = stmt.executeQuery(sql)) {
			while (resultSet.next()) {
				javaDevelopers.add(resultSet.getString("firstName"));
			}
		}
		return javaDevelopers;
	}

}
