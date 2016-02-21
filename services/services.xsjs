// do global replace on neo_ and p000 first

function reset() {
    var sqlstmt;
    var conn;
    conn = $.db.getConnection();
    sqlstmt = 'SET SCHEMA NEO_';
    conn.prepareStatement(sqlstmt).execute();
    sqlstmt = 'TRUNCATE TABLE "Tweets"';
    conn.prepareStatement(sqlstmt).execute();
    sqlstmt = 'TRUNCATE TABLE PAL_RESULTS';
    conn.prepareStatement(sqlstmt).execute();
    sqlstmt = 'TRUNCATE TABLE PAL_CENTERS';
    conn.prepareStatement(sqlstmt).execute();
    sqlstmt = 'DROP FULLTEXT INDEX "tweets"'; 
    conn.prepareStatement(sqlstmt).execute();
    sqlstmt = 'CREATE FULLTEXT INDEX "tweets" ON "Tweets"("text") CONFIGURATION \'EXTRACTION_CORE_VOICEOFCUSTOMER\' LANGUAGE COLUMN "lang" LANGUAGE DETECTION (\'EN\',\'FR\',\'DE\',\'ES\',\'ZH\') TEXT ANALYSIS ON';
    conn.prepareCall(sqlstmt).execute();
    conn.commit();
    conn.close();
}

function cluster() {
	var sqlstmt;
	var conn;
	var pstmt;
	var pcall;
	var rs;
	conn = $.db.getConnection();
	sqlstmt = 'SET SCHEMA NEO_';
	conn.prepareStatement(sqlstmt).execute();
	sqlstmt = 'TRUNCATE TABLE PAL_RESULTS';
	conn.prepareStatement(sqlstmt).execute();
	sqlstmt = 'TRUNCATE TABLE PAL_CENTERS';
	conn.prepareStatement(sqlstmt).execute();
	sqlstmt = 'CALL _SYS_AFL."NEO__TWEETERS_CLUSTER"("Tweeters", PAL_PARAMS, ?, ?) with overview';
	pcall = conn.prepareCall(sqlstmt);
	rs = pcall.execute();
	rs = pcall.getResultSet();
	var tables = [];
	while (rs.next()) { 
		tables.push(rs.getString(2));
	}
	pstmt = conn.prepareStatement('INSERT INTO PAL_RESULTS SELECT * FROM ' + tables[0]);
	pstmt.executeUpdate();  
	pstmt.close(); 
	pstmt = conn.prepareStatement('INSERT INTO PAL_CENTERS SELECT * FROM ' + tables[1]);
	pstmt.executeUpdate();  
	conn.commit();
	conn.close();
}

if ($.session.hasAppPrivilege("p000trial.dev.smartapp::Execute")) {
	var cmd = $.request.parameters.get('cmd');  
	switch (cmd) {  
		case "reset":
			reset();
			break; 
		case "cluster":
			cluster();
			break; 
		default:  
			$.response.setBody('Invalid Command: ' + cmd);  
			$.response.status = $.net.http.INTERNAL_SERVER_ERROR;  
	}
} else {
	$.response.setBody("Not authorized."); 
	$.response.status = $.net.http.INTERNAL_SERVER_ERROR;
}
