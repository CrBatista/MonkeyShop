db.createCollection("customer_events")
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
        }
    ]
 )