William Fan
101300501

Install and setup postgresql:
```
sudo apt install postgresql
sudo -u postgres psql -c "CREATE DATABASE student_db;"
sudo -u postgres psql -d student_db -f assignment3.sql 
sudo -u postgres psql -c "ALTER USER postgres WITH PASSWORD 'NewPassword123';"
sudo apt install libpostgresql-jdbc-java -y
```

Build and run application:
```
javac --release 8 -cp /usr/share/java/postgresql.jar PostgreSQLJDBCConnection.java
java -cp .:/usr/share/java/postgresql.jar PostgreSQLJDBCConnection
```
