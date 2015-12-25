/*
 * Copyright 2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package leap.db.cp;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import leap.lang.jdbc.StatementWrapper;

public class ProxyStatement extends StatementWrapper {
	
	protected final PooledConnection conn;

	protected Thread threadOnCreate;
	
	protected long   lastExecutingTime;
	protected long   lastExecutingDurationMs;
	protected String lastExecutingSql;

	ProxyStatement(PooledConnection conn, Statement stmt) {
		super(stmt);
		this.conn = conn;
		this.threadOnCreate = Thread.currentThread();
		//this.stackTraceExceptionOnCreate = new IllegalStateException("");
	}
	
	public Thread getThreadOnCreate() {
		return threadOnCreate;
	}
	
	public StackTraceElement[] getStackTraceOfThreadOnCreate() {
		return threadOnCreate.getStackTrace();
	}

	public Statement getReal() {
		return stmt;
	}

	@Override
    public Connection getConnection() throws SQLException {
		return conn;
	}
	
	protected ProxyResultSet createResultSetProxy(ResultSet rs) {
		return new ProxyResultSet(this,rs);
	}
	
	public String getLastExecutingSql() {
		return lastExecutingSql;
	}

	public long getLastExecutingTime() {
		return lastExecutingTime;
	}
	
	public long getLastExecutingDurationMs() {
		return lastExecutingDurationMs;
	}

	protected void beginExecute(String sql) {
		lastExecutingTime     = System.currentTimeMillis();
		lastExecutingDurationMs = -1;
		
		if(null != sql) {
			lastExecutingSql = sql;
		}
	}
	
	protected void endExecute() {
		lastExecutingDurationMs = System.currentTimeMillis() - lastExecutingTime;
		lastExecutingTime     = -1;
	}

	@Override
    public void close() throws SQLException {
		conn.closeStatement(this, stmt);
    }

	@Override
    public ResultSet getResultSet() throws SQLException {
	    return createResultSetProxy(super.getResultSet());
    }

	@Override
    public ResultSet getGeneratedKeys() throws SQLException {
	    return createResultSetProxy(super.getGeneratedKeys());
    }

	@Override
    public ResultSet executeQuery(String sql) throws SQLException {
		try{
			beginExecute(sql);
			return createResultSetProxy(stmt.executeQuery(sql));
		}finally{
			endExecute();
		}
    }

	@Override
    public int executeUpdate(String sql) throws SQLException {
		try{
			beginExecute(sql);
			return stmt.executeUpdate(sql);
		}finally{
			endExecute();
		}
    }

	@Override
    public boolean execute(String sql) throws SQLException {
		try{
			beginExecute(sql);
			return stmt.execute(sql);
		}finally{
			endExecute();
		}
    }
	
	@Override
    public void addBatch(String sql) throws SQLException {
		this.lastExecutingSql = sql;
	    super.addBatch(sql);
    }

	@Override
    public int[] executeBatch() throws SQLException {
		try{
			beginExecute(null);
			return stmt.executeBatch();
		}finally{
			endExecute();
		}
    }

	@Override
    public int executeUpdate(String sql, int autoGeneratedKeys) throws SQLException {
		try{
			beginExecute(sql);
			return stmt.executeUpdate(sql, autoGeneratedKeys);
		}finally{
			endExecute();
		}
	}

	@Override
    public int executeUpdate(String sql, int[] columnIndexes) throws SQLException {
		try{
			beginExecute(sql);
			return stmt.executeUpdate(sql, columnIndexes);
		}finally{
			endExecute();
		}
    }

	@Override
    public int executeUpdate(String sql, String[] columnNames) throws SQLException {
		try{
			beginExecute(sql);
			return stmt.executeUpdate(sql, columnNames);
		}finally{
			endExecute();
		}
    }

	@Override
    public boolean execute(String sql, int autoGeneratedKeys) throws SQLException {
		try{
			beginExecute(sql);
			return stmt.execute(sql, autoGeneratedKeys);
		}finally{
			endExecute();
		}
    }

	@Override
    public boolean execute(String sql, int[] columnIndexes) throws SQLException {
		try{
			beginExecute(sql);
			return stmt.execute(sql, columnIndexes);
		}finally{
			endExecute();
		}
    }

	@Override
    public boolean execute(String sql, String[] columnNames) throws SQLException {
		try{
			beginExecute(sql);
			return stmt.execute(sql, columnNames);
		}finally{
			endExecute();
		}
    }
}
