db.createCollection("user_events")
db.createView("users", "user_events",
    [
        {
            $sort: { timestamp: 1 }
        },
        {
            $group: {
                _id: '$userId',
                data: {
                    $mergeObjects: '$data'
                },
                createdBy: {
                    $first: '$author'
                },
                createdAt: {
                    $first: '$timestamp'
                }, updatedBy: {
                    $last: '$author'
                }, updatedAt: {
                    $last: '$timestamp'
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
        }
    ]
 )