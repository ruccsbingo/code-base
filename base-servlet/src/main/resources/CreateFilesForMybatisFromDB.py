__author__ = 'zhangbing'

import MySQLdb as mdb
import os
import datetime
import xml.etree.ElementTree as ET
from xml.etree.ElementTree import Element
import random


# out variable
tableName = "approvers"
autoID = False
visibility = "private"

# variable
coreDataMap = {}
workPath = "/Users/zhangbing/IdeaProjects/Development/code-base/base-servlet"
testPath = "/src/test/java/com/bingo/"
packagePath = "/src/main/java/com/bingo/"
classPath = "com.bingo"
resourcePath = "/src/main/resources"

# db settings
db_host = '10.101.1.140'
db_name = 'worker'
db_password = 'services'
db_table = 'wemedia_for_manual'

# function
def underline_to_camel(underline_format):
    camel_format = ''
    if isinstance(underline_format, str):
        if underline_format == "id":
            camel_format = "id"
        else:
            for _s_ in underline_format.split('_'):
                if _s_ == "id":
                    camel_format += "ID"
                else:
                    camel_format += _s_.capitalize()
            camel_format = camel_format[0].lower() + camel_format[1:]
    return camel_format

# Get the columns for a table from DB
try:
    conn = mdb.connect(db_host, db_name, db_password, db_table)
    cur = conn.cursor()
    cur.execute("show columns from " + tableName)
    rows = cur.fetchall()
    for row in rows:
        coreDataMap[row[0]] = {}
        # db column name
        coreDataMap[row[0]]["tColumnName"] = row[0]

        # data type
        s = row[1]
        if s[0:3] == 'int' or s[0:7] == 'tinyint':
            coreDataMap[row[0]]["type"] = "Integer"
        elif s[0:6] == "bigint":
            coreDataMap[row[0]]["type"] = "Long"
        elif s[0:7] == "varchar" or s[-4:] == "text":
            coreDataMap[row[0]]["type"] = "String"
        else:
            print row[0], row[1]
            coreDataMap[row[0]]["type"] = "String"

        # table column name
        coreDataMap[row[0]]["cColumnName"] = underline_to_camel(row[0])
    print coreDataMap
finally:
    conn.close()
className = underline_to_camel(tableName)
className = className[0].capitalize() + className[1:]

# Create PO.class
if not os.path.isdir(workPath + packagePath + "/po"):
    os.makedirs(workPath + packagePath + "/po", 0777)
os.chdir(workPath + packagePath + "/po")
poFile = open(className + "Po.java", "w")
poFile.write("package %s.po;\n\n" % classPath)
poFile.write("import com.fasterxml.jackson.annotation.JsonProperty;\n\n")
poFile.write("/**\n")
poFile.write(" * PO to link %s in DataBase\n" % tableName)
poFile.write(" * Created at %s\n" % datetime.datetime.now().strftime("%Y-%m-%d"))
poFile.write(" */\n")
poFile.write("public class %sPo {\n" % className)
for name in coreDataMap:
    item = coreDataMap[name]
    poFile.write("\t")
    poFile.write('@JsonProperty("%s")\n' % item["tColumnName"])
    poFile.write("\t")
    poFile.write("%s %s %s;\n" % (visibility, item["type"], item["cColumnName"]))
poFile.write("\n\t//Only for test, Do not use in normal function\n")
poFile.write("\tpublic void fillValueForTest(){\n")
for name in coreDataMap:
    if name != "id":
        item = coreDataMap[name]
        poFile.write("\t\tthis.%s = " % item["cColumnName"])
        if item["type"] == "Integer":
            poFile.write("%d" % random.randint(1, 50))
        elif item["type"] == "Long":
            poFile.write("%dL" % random.randint(1, 50))
        else:
            poFile.write("\"String for Test %d\"" % random.randint(1, 10))
        poFile.write(";\n")
    elif not autoID:
        poFile.write("\t\tid = %dL;\n" % random.randint(1, 50))
poFile.write("\t}\n")
for name in coreDataMap:
    item = coreDataMap[name]
    cN = item["cColumnName"][0].capitalize() + item["cColumnName"][1:]
    poFile.write("\n\tpublic %s get%s() {\n\t\treturn %s;\n\t}\n" %
                 (item["type"], cN, item["cColumnName"]))
    poFile.write("\n\tpublic void set%s(%s %s) {\n\t\tthis.%s = %s;\n\t}\n" %
                 (cN, item["type"], item["cColumnName"], item["cColumnName"], item["cColumnName"]))
poFile.write("\n\tpublic void updatePO(%sPo inPo){\n" % className)
for name in coreDataMap:
    item = coreDataMap[name]
    poFile.write("\t\tif (inPo.%s != null)\n\t\t\tthis.%s = inPo.%s;\n" % (
        item["cColumnName"], item["cColumnName"], item["cColumnName"]))
poFile.write("\t}\n\n")
for name in coreDataMap:
    item = coreDataMap[name]
    poFile.write('\t public static final String %s_%s = "%s";\n' % (
        tableName.upper(), item["tColumnName"].upper(), item["tColumnName"]))
poFile.write("}\n")
poFile.flush()
poFile.close()


# Create dao class
if not os.path.isdir(workPath + packagePath + "/dao"):
    os.makedirs(workPath + packagePath + "/dao", 0777)
os.chdir(workPath + packagePath + "/dao")
daoFile = open(className + "Dao.java", "w")
daoFile.write("package %s.dao;\n\n" % classPath)
daoFile.write("import %s.po.%sPo;\n\n" % (classPath, className));
daoFile.write("/**\n")
daoFile.write(" * Dao to manager the sql function for table %s\n" % tableName)
daoFile.write(" * Created at %s\n" % datetime.datetime.now().strftime("%Y-%m-%d"))
daoFile.write(" */\n")
daoFile.write("public interface %s extends BasicDataManager<%s> {\n\n}\n" % (className + "Dao", className + "Po"))
daoFile.flush()
daoFile.close()

# Create mybatis xml class
if not os.path.isdir(workPath + resourcePath + "/mybatis"):
    os.makedirs(workPath + resourcePath + "/mybatis", 0777)
os.chdir(workPath + resourcePath + "/mybatis")
xmlFile = open(className + "Dao.xml", "w")
xmlFile.write("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n")
xmlFile.write(
    "<!DOCTYPE mapper PUBLIC \"-//mybatis.org//DTD Mapper 3.0//EN\" \"http://mybatis.org/dtd/mybatis-3-mapper.dtd\">\n")
xmlFile.write("<mapper namespace=\"%s.dao.%s\">\n\n" % (classPath, className + "Dao"))

xmlFile.write("\t<resultMap id=\"%s\" type=\"%s.po.%sPo\">\n" % (className, classPath, className))
xmlFile.write("\t\t<id property=\"id\" column=\"id\"/>\n")
for name in coreDataMap:
    item = coreDataMap[name]
    if name != "id":
        xmlFile.write("\t\t<result property=\"%s\" column=\"%s\"/>\n" % (item["cColumnName"], item["tColumnName"],))
xmlFile.write("\t</resultMap>\n\n")

xmlFile.write("\t<sql id=\"tableName\">%s</sql>\n\n" % tableName)

xmlFile.write("\t<!-- basic function, defined in the super dao class -->\n")
xmlFile.write("\t<select id=\"countAll\" resultType=\"int\">\n"
              "\t\tSELECT count(*) FROM <include refid=\"tableName\"/>;\n"
              "\t</select>\n\n")

xmlFile.write("\t<select id=\"getByPK\" parameterType=\"long\" resultMap=\"%s\">\n"
              "\t\tSELECT * FROM <include refid=\"tableName\"/> where `id` = #{id};\n"
              "\t</select>\n\n" % className)

xmlFile.write("\t<select id=\"getAll\" resultMap=\"%s\">\n"
              "\t\tSELECT * FROM <include refid=\"tableName\"/>;\n"
              "\t</select>\n\n" % className)

xmlFile.write(
    "\t<insert id=\"insert\" parameterType=\"%sPO\" useGeneratedKeys=\"true\" keyProperty=\"id\">\n" % className)
tempColumn = ""
tempValue = ""
for name in coreDataMap:
    if name != "id":
        item = coreDataMap[name]
        tempColumn += ", `%s`" % item["tColumnName"]
        tempValue += ", #{%s}" % item["cColumnName"]
    elif not autoID:
        item = coreDataMap[name]
        tempColumn += ", `%s`" % item["tColumnName"]
        tempValue += ", #{%s}" % item["cColumnName"]
xmlFile.write("\t\tREPLACE INTO <include refid=\"tableName\"/>\n")
xmlFile.write("\t\t(%s)\n" % tempColumn[2:])
xmlFile.write("\t\tVALUES(%s);\n\t</insert>\n\n" % tempValue[2:])

xmlFile.write("\t<update id=\"updateFields\" parameterType=\"java.util.Map\">\n")
xmlFile.write("\t\tUPDATE <include refid=\"tableName\"/>\n")
xmlFile.write("\t\t<trim prefix=\"SET\" suffixOverrides=\",\">\n")
for name in coreDataMap:
    if name != "id":
        item = coreDataMap[name]
        xmlFile.write("\t\t\t<if test=\"map.%s != null\"> `%s` = #{map.%s},</if>\n" % (
            item["cColumnName"], item["tColumnName"], item["cColumnName"]))
xmlFile.write("\t\t</trim>\n\t\tWHERE `id` = #{id};\n\t</update>\n\n")

xmlFile.write("\t<update id=\"updateAll\" parameterType=\"%sPO\">\n" % className)
xmlFile.write("\t\tUPDATE <include refid=\"tableName\"/>\n")
xmlFile.write("\t\tSET\n")
tempValue = ""
for name in coreDataMap:
    if name != "id":
        item = coreDataMap[name]
        tempValue += ",\n\t\t`%s`= #{%s}" % (item["tColumnName"], item["cColumnName"])
xmlFile.write(tempValue[2:])
xmlFile.write("\n\t\tWHERE `id`= #{id};\n\t</update>\n\n")

xmlFile.write("\t<delete id =\"realDelete\" parameterType=\"long\">\n")
xmlFile.write("\t\tDELETE FROM <include refid=\"tableName\"/>\n")
xmlFile.write("\t\tWHERE `id` = #{id}\n\t</delete>\n")
xmlFile.write("</mapper>")
xmlFile.flush()
xmlFile.close()


# insert into mybatis mapper
configPath = workPath + resourcePath + "/mybatis-config.xml";
configTree = ET.parse(configPath)

configPath = open(configPath, "w")
configPath.write("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n"
                 "<!DOCTYPE configuration PUBLIC \"-//mybatis.org//DTD Config 3.0//EN\"\n"
                 "\"http://mybatis.org/dtd/mybatis-3-config.dtd\">\n")

root = configTree.find("mappers")
nodes = root.findall("mapper")
mark = False
for node in nodes:
    if node.get("resource") == ("mybatis/%s" % className + "Dao.xml"):
        mark = True
if not mark:
    newNode = Element("mapper", {"resource": "mybatis/%s" % className + "Dao.xml"})
    root.append(newNode)
doctypeStr = "<!DOCTYPE configuration PUBLIC \"-//mybatis.org//DTD Config 3.0//EN" \
             "\"\n\"http://mybatis.org/dtd/mybatis-3-config.dtd\">"
configTree.write(configPath, encoding="utf-8", xml_declaration=False)


# unit test
#if not os.path.isdir(workPath + testPath + "/test/dao"):
#    os.makedirs(workPath + testPath + "/test/dao", 0777)
#os.chdir(workPath + testPath + "/test/dao")
#testFile = open(className + "DaoTest.java", "w")
#testFile.write("package %s.test.dao;\n\nimport org.junit.Test;\n\n" % classPath)
#testFile.write("import %s.dao.%sDao;\n" % (classPath, className))
#testFile.write("import %s.po.%sPo;\n" % (classPath, className))
#testFile.write("import com.yidian.weMedia.SDK.util.MyBatisUtil;\n"
#               "import org.apache.ibatis.session.SqlSession;\n\n")
#testFile.write("public class %sDaoTest {\n" % className)
#testFile.write("\t@Test\n")
#testFile.write("\tpublic void testCount() {\n")
#testFile.write("\t\t%sPo po = new %sPo();\n" % (className, className))
#testFile.write("\t\tpo.fillValueForTest();\n")
#testFile.write("\t\tSqlSession sqlSession = MyBatisUtil.getSqlSession();\n")
#testFile.write("\t\t%sDao dao = sqlSession.getMapper(%sDao.class);\n" % (className, className))
#testFile.write("\t\tint res = dao.countAll();\n\t\tdao.insert(po);\n"
#              "\t\tsqlSession.commit();\n"
#               "\t\tassert (dao.countAll() - res == 1);\n")
#testFile.write("\t\tsqlSession.close();\n\t}\n}")

