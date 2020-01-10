<!--
 ___ _            _ _    _ _    __
/ __(_)_ __  _ __| (_)__(_) |_ /_/
\__ \ | '  \| '_ \ | / _| |  _/ -_)
|___/_|_|_|_| .__/_|_\__|_|\__\___|
            |_| 
-->
![](https://docs.simplicite.io//logos/logo250.png)
* * *

`Trello` module definition
==========================

Trello API examples

`TrelloCardExample` business object definition
----------------------------------------------

Trello example object 1

### Fields

| Name                                                         | Type                                     | Required | Updatable | Personal | Description                                                                      | 
| ------------------------------------------------------------ | ---------------------------------------- | -------- | --------- | -------- | -------------------------------------------------------------------------------- |
| `trelloCardExName`                                           | char(100)                                | yes*     | yes       |          | -                                                                                |
| `trelloCardExDescription`                                    | text(1000000)                            |          | yes       |          | -                                                                                |
| `trelloCardExCardID`                                         | char(50)                                 |          |           |          | Trello card ID                                                                   |

### Custom actions

No custom action

`TrelloAPIClientExample` external object definition
---------------------------------------------------

Trello API client example


`TrelloWebhook` external object definition
------------------------------------------

Trello webhook:

```
curl -X POST -H "Content-Type: application/json" https://api.trello.com/1/tokens/<token>/webhooks/ -d '{
  "key": "<key>",
  "callbackURL": "https://<base URL>/ext/TrelloWebhook",
  "idModel":"<board ID>",
  "description": "Demo webhook"
}'
```


