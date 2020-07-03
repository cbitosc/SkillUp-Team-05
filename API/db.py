from flask import jsonify
from decimal import Decimal
import pymysql
import datetime

def query(querystr,return_json=True):
    connection=pymysql.Connect(host='skillup-team-05.cxgok3weok8n.ap-south-1.rds.amazonaws.com',
                               user='admin',password='coscskillup',
                               db='stdnt',
                               cursorclass=pymysql.cursors.DictCursor)
    connection.begin()
    cursor=connection.cursor()
    cursor.execute(querystr)
    result=encode(cursor.fetchall())
    connection.commit()
    cursor.close()
    connection.close()
    if return_json:
        return jsonify(result)
    else:
       return result

def encode(data):
    for row in data:
        for key,value in row.items():
            if isinstance(value,Decimal):
                row[key]=str(value)
            if isinstance(value,datetime.date):
                row[key]=value.isoformat()
            if isinstance(value,datetime.timedelta):
                row[key]=str(value)
    return data
