package Servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.bson.Document;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class MongoDB_Insert
{	
	public MongoDB_Insert()
	{		
	}
	
	static public void FingerInsert(Integer user_age, String user_sex, String user_fun, String question_area, String tendercy1, String tendercy2, String tendercy3)
	{
		MongoClient mongo = new MongoClient("lim7504.iptime.org", 27017);
		MongoDatabase db =  mongo.getDatabase("TRIPTALK");	
		MongoCollection<Document> collFinger = db.getCollection("FINGER");

		
		Document doc = new Document("USER_AGE", user_age)
							.append("USER_SEX", user_sex)
							.append("USER_FUN", user_fun)
							.append("QUESTION_AREA", question_area)
							.append("TENDERCY1", tendercy1)
							.append("TENDERCY2", tendercy2)
							.append("TENDERCY3", tendercy3);
		
			
		collFinger.insertOne(doc);
		
		mongo.close();
	}
}
