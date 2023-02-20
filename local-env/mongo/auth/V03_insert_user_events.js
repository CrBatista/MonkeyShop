db.getCollection("user_events").insert({
    "_id" : ObjectId(),
    "data" : {
        "username" : "Cristian",
        "email" : "cristian@yopmail.com",
        "passwordHash" : "$2a$12$pAMKImy054zibMO/g35TbuP7nnizl9/0emMirkN/vQPrLj3CKkGZe",
        "role" : "ADMIN"
    },
    "userId" : "f62dddb2-d700-4980-9314-26cda12bd191",
    "type" : "create",
    "timestamp" : ISODate()
})

// Password: Password123