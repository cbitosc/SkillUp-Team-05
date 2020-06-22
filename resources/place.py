from  flask import jsonify
from werkzeug.security import safe_str_cmp
from flask_jwt_extended import create_access_token,jwt_required
from flask_restful import Resource,reqparse
from db import query

class Phase(Resource):
    def get(self):
        parser=reqparse.RequestParser()
        parser.add_argument('roll_no',type=int,required=True,help='roll_no cannot be left blank')
        data=parser.parse_args()
        try:
           x=query(f"""select * from stdnt.stdetails where roll_no={data['roll_no']};""",return_json=False)
           if len(x)>0:return query(f"""select * from stdnt.stdetails where roll_no={data['roll_no']};""",return_json=False)
           else: return {"message":"NO details found first sign up as student."},400 
        except:
            return {"message":"There was an error cannot connect"},500
    
    def post(self):
         parser=reqparse.RequestParser()
         parser.add_argument('roll_no',type=int,required=True,help='roll_no cannot be left blank')
         parser.add_argument('dept_name',type=str,required=True,help='dept_name cannot be left blank')
         parser.add_argument('first_name',type=str,required=True,help='first_name cannot be left blank')
         parser.add_argument('last_name',type=str,required=True,help='last_name cannot be left blank')
         parser.add_argument('gpa',type=str,required=True,help='gpa cannot be left blank')
         parser.add_argument('password',type=str,required=True,help='password cannot be left blank')
         parser.add_argument('dateregst',type=str,required=True,help='dateregst cannot be left blank')
         data=parser.parse_args()
         try:
            x=query(f"""SELECT * FROM stdnt.deptdetails where dept_name='{data['dept_name']}';""",return_json=False)
            if (len(x)==0):
                query(f"""insert into stdnt.deptdetails values('{data['dept_name']}');""",return_json=False)
         except:
            return {"message":"There was an error cannot connect"},500

        
         try:
             x=query(f"""select * from stdnt.stdetails where roll_no={data['roll_no']};""",return_json=False)
             if len(x)>0:return {"message":"student details with that info already exists"},400
        
         except:
            return {"message":"There was an error cannot connect"},500
         try:
            query(f"""insert into stdnt.stdetails values({data['roll_no']},
                                                             '{data['dept_name']}',
                                                             '{data['first_name']}',
                                                              '{data['last_name']}',
                                                              '{data['gpa']}',
                                                              '{data['password']}',
                                                              '{data['dateregst']}');""")
         except:
                return{"message":"there was an error in signup insert "},500
         return {"message":"success signup."},201
         

class User():
    def __init__(self,roll_no,password):
        self.roll_no=roll_no
        self.password=password

    @classmethod
    def getUserByRoll(cls,roll_no):
        result=query(f"""select roll_no,password from stdetails where roll_no={roll_no}""",return_json=False)
        if len(result)>0: return User(result[0]['roll_no'],result[0]['password'])
        return None
    
class PlaceLogin(Resource):
    def post(self):
        parser=reqparse.RequestParser()
        parser.add_argument('roll_no',type=int,required=True,help='roll_no cannot be left blank')
        parser.add_argument('password',type=str,required=True,help='password cannot be left blank')
        data=parser.parse_args()
        user=User.getUserByRoll(data['roll_no'])
        if user and safe_str_cmp(user.password,data['password']):
           x=query(f"""select gpa from stdnt.stdetails where roll_no={data['roll_no']};""",return_json=False)
           if (len(x)==1):
              y=query(f"""select min(min_gpa) from stdnt.posting;""",return_json=False)
              if float(x[0]['gpa'])>=float(y[0]['min(min_gpa)']):
                try:
                    return query(f"""select * from stdnt.posting where min_gpa=any(select min_gpa from stdnt.posting where {float(x[0]['gpa'])}>=posting.min_gpa);""")
                except:
                    return{"message":"there was an error cannot connect"},500
              else:
                return {"message":"No company hires you"},400
           else:
               return {"message":"There was an error cannot connect"},500
        return {'message':"Invalid Credentials"},401
class Registerstud(Resource):
    def post(self):
        parser=reqparse.RequestParser()
        parser.add_argument('roll_no',type=int,required=True,help='roll_no cannot be left blank')
        parser.add_argument('company_name',type=str,required=True,help='company_name cannot be left blank')
        parser.add_argument('dateregco',type=str,required=True,help='dateregco cannot be left blank')
        data=parser.parse_args()
        k=query(f"""select roll_no,gpa from stdnt.stdetails where roll_no={data['roll_no']};""",return_json=False)
        if (len(k)>0):
          y=query(f"""select min_gpa from stdnt.posting where company_name='{data['company_name']}';""",return_json=False)
          if len(y)>0:
           if float(k[0]['gpa'])>=float(y[0]['min_gpa']):
            try:
               x=query(f"""select * from stdnt.companyreg where roll_no={data['roll_no']};""",return_json=False)
               if len(x)>0:return {"message":"Student with that info already registered"},400
        
            except:
               return {"message":"There was an error cannot connect"},500
            try:
                query(f"""insert into stdnt.companyreg values('{data['company_name']}',
                                                             {data['roll_no']},
                                                             '{data['dateregco']}');""")
            except:
                return{"message":"there was an error inserting to companies"},500
            return {"message":"successfully registered."},201
           else:
              return {"message":"Your gpa is not sufficient to register for this company."},400 
          else:
              return {"message":"There was no company with that name"},500 
        else:
           return {"message":"Your Details are not supported first sign up as student."},400 
class UserAnnouncecheck(Resource):
    def get(self):
        try:
          x=query(f"""select * from stdnt.announcements;""",return_json=False)  
          if len(x)>0:return query(f"""select * from stdnt.announcements;""",return_json=False)  
          else:return {'message':'No Announcements yet.'}
        except:
          return  {"message":"Error to connect"},500 


class Checkusers(Resource):
    def get(self):
        try:
          return query(f"""select * from stdnt.stdetails;""",return_json=False)
        except:
          return  {"message":"Error to connect"},500 
    
class Userregistrstiondet(Resource):
    def get(self):
        parser=reqparse.RequestParser()
        parser.add_argument('company_name',type=str,required=True,help='company_name cannot be left blank')
        data=parser.parse_args()
        try:
           y=query(f"""select roll_no from stdnt.companyreg where company_name='{data['company_name']}';""",return_json=False)
           if len(y)>0:
             return query(f"""select roll_no,dept_name,first_name,last_name,gpa from stdnt .stdetails where roll_no=any(select roll_no from stdnt.companyreg where companyreg.company_name='{data['company_name']}');
;""",return_json=False)
           else:
             return {"message":"No one registered to your company"},400 
        except:
            return  {"message":"Error to connect"},500 
    

    
class AdminAnnounce(Resource):
    def post(self):
        parser=reqparse.RequestParser()
        parser.add_argument('type',type=str,required=True,help='type cannot be left blank')
        parser.add_argument('title_description',type=str,required=True,help='title_description cannot be left blank')
        parser.add_argument('date_event',type=str,required=True,help='date_event cannot be left blank')
        parser.add_argument('time_event',type=str,required=True,help='time_event cannot be left blank')
        parser.add_argument('event_description',type=str,required=True,help='event_description cannot be left blank')
        parser.add_argument('image_link',type=str)
        data=parser.parse_args()
        if data['image_link']!=None:
            try:
              query(f"""insert into stdnt.announcements values('{data['type']}',
                                                             '{data['title_description']}',
                                                             '{data['date_event']}',
                                                              '{data['time_event']}',
                                                              '{data['event_description']}',
                                                              '{data['image_link']}');""")
            except:
                return{"message":"there was an error unable to add announcements"},500
            return {"message":"success inserted."},201
        else:
            try:
              query(f"""insert into stdnt.announcements(type,title_description,date_event,time_event,event_description) values('{data['type']}',
                                                             '{data['title_description']}',
                                                             '{data['date_event']}',
                                                              '{data['time_event']}',
                                                              '{data['event_description']}');""")
            except:
                return{"message":"there was an error unable to add announcements"},500
            return {"message":"success inserted."},201 
        
class Addcompany(Resource):
    def post(self):
        parser=reqparse.RequestParser()
        parser.add_argument('company_name',type=str,required=True,help='company-name cannot be left blank')
        parser.add_argument('job_name',type=str,required=True,help='job_name cannot be left blank')
        parser.add_argument('job_description',type=str,required=True,help='job_description cannot be left blank')
        parser.add_argument('job_requirements',type=str,required=True,help='job_requirements cannot be left blank')
        parser.add_argument('min_gpa',type=str,required=True,help='min_gpa cannot be left blank')
        data=parser.parse_args()
        try:
           x=query(f"""SELECT * FROM stdnt.company where company_name='{data['company_name']}';""",return_json=False)
           if (len(x)==0):
                query(f"""insert into stdnt.company values('{data['company_name']}');""",return_json=False)
        except:
            return {"message":"There was an error cannot connect"},500

        try:
            y=query(f"""select company_name from stdnt.posting where company_name='{data['company_name']}' and job_name='{data['job_name']}';""",return_json=False)
            if len(y)>0:return {"message":"Already added this job_name for this company to the table"},400
        except:
            return {"message":"There was an error cannot connect"},500

        try:
            query(f"""insert into stdnt.posting values('{data['company_name']}',
                                                             '{data['job_name']}',
                                                             '{data['job_description']}',
                                                              '{data['job_requirements']}',
                                                              '{data['min_gpa']}');""")
        except:
                return{"message":"there was an error in inserting your company"},500
        return {"message":"success added."},201

class DateStatsforplace(Resource):
    def get(self):
        parser=reqparse.RequestParser()
        parser.add_argument('Date',type=str,required=True,help='Date cannot be left blank')
        data=parser.parse_args()
        try:
            x=query(f"""SELECT * FROM stdnt.stdetails where dateregst='{data['Date']}';""",return_json=False)
            if len(x)>0:return query(f"""SELECT roll_no,dept_name,first_name,last_name,gpa FROM stdnt.stdetails where dateregst='{data['Date']}';""",return_json=False)
            else:return{"message":"No student registered for placements on this day"},400
        except:
            return {"message":"There was an error cannot connect"},500

class Datestatsforcom(Resource):
    def get(self):
        parser=reqparse.RequestParser()
        parser.add_argument('Date',type=str,required=True,help='Date cannot be left blank')
        data=parser.parse_args()
        try:
            x=query(f"""SELECT * FROM stdnt.companyreg where dateregco='{data['Date']}';""",return_json=False)
            if len(x)>0:return query(f"""SELECT roll_no,company_name FROM stdnt.companyreg where dateregco='{data['Date']}';""",return_json=False)
            else:return{"message":"No student registered for company on this day"},400
        except:
            return {"message":"There was an error cannot connect"},500

class Datestatsforann(Resource):
    def get(self):
        parser=reqparse.RequestParser()
        parser.add_argument('Date',type=str,required=True,help='Date cannot be left blank')
        data=parser.parse_args()
        try:
            x=query(f"""SELECT * FROM stdnt.announcements where date_event='{data['Date']}';""",return_json=False)
            if len(x)>0:return query(f"""SELECT type,title_description,time_event FROM stdnt.announcements where date_event='{data['Date']}';""",return_json=False)
            else:return{"message":"No Announcements for this day"},400
        except:
            return {"message":"There was an error cannot connect"},500




