HOSTNAME = "localhost"
PORT = 27017
import pymongo

client = pymongo.MongoClient(HOSTNAME, PORT)
db = client.news
# collection = db.itscience
# collection = db.economy
collection = db.society
def save(date,headlineList,mainList):
    collection.save({"date": date, "headline": headlineList, "main":mainList})
