package Servlet;

import org.bson.Document;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class InsertItem {

	public void Insert(MongoCollection<Document> coll, Document doc)
	{
		
//		MongoClient mongo = new MongoClient("lim7504.iptime.org", 27017);
//		MongoDatabase db =  mongo.getDatabase("test");	
//		MongoCollection<Document> coll = db.getCollection("emp");
//
//		
//		Document doc = new Document("empno", 666);
//		// Document�� Row�� ����.
//		// "empno" �÷����̴�.
//		// 666        ���̴�.
//		// �߰��� �÷��� ���� �ִٸ� append�Լ��� �ڿ� �ٿ� ����ϸ�ȴ�.
//		// MongoDB�� Table ��Ű���� ���� ������ 
//		// �÷����� append("���ο��÷�", ��)�̿� ���� ���� �߰��Ͽ��� Insert�ȴ�.
//		// ���ڿ��� �÷��� ���ڿ� �����͸� �־ �ȴ�. �÷� ������ Mixed�� �ڵ���ȯ�� �ȴ�.
//
		coll.insertOne(doc);
//
//		//Ư���� ������� �� �������� �ش� Document Insert�� �ȴ�.
//		
//		mongo.close();
	}
	
	
}
