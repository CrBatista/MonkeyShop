db.createView("customers", "customer_events",
    [
            {
                $sort: { timestamp: 1 }
            },
            {
                $group: {
                    _id: '$customerId',
                    data: {
                        $mergeObjects: '$data'
                    },
                    createdBy: {
                        $first: '$author'
                    },
                    createdAt: {
                        $first: '$timestamp'
                    },
                    updatedBy: {
                        $last: '$author'
                    },
                    updatedAt: {
                        $last: '$timestamp'
                    },
                    lastEvent: {
                        $last: '$type'
                    },
                    history: {
                        $push: {
                            type: '$type',
                            author: '$author',
                            timestamp: '$timestamp',
                            data: '$data'
                        }
                    }
                }
            },
            {
               $match: {
                   lastEvent: {$not: { $eq: "deleted" } }
               }
            },
            {
                $lookup: {
                    from: "users",
                    localField: "createdBy",
                    foreignField: "_id",
                    as: "createdBy"
                }
            },
            {
                $lookup: {
                    from: "users",
                    localField: "updatedBy",
                    foreignField: "_id",
                    as: "updatedBy"
                }
            },

            {
                $unwind: {
                    path: "$createdBy",
                    preserveNullAndEmptyArrays: true
                }
            },
            {
                $unwind: {
                    path: "$updatedBy",
                    preserveNullAndEmptyArrays: true
                }
            },
            {
                $project: {
                    _id: 1,
                    data: 1,
                    createdBy: {
                        "_id": 1,
                        "username": "$createdBy.data.username",
                        "email": "$createdBy.data.email"
                    },
                    createdAt: 1,
                    updatedBy: {
                        "_id": 1,
                        "username": "$updatedBy.data.username",
                        "email": "$updatedBy.data.email"
                    },
                    updatedAt: 1,
                    lastEvent: 1,
                    history: 1
                }
            }
        ]
 )