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

### Introduction

Trello integration examples:

- A business object that synchronizes with Trello cards (using a webhook for incoming updates from Trello)
- A Trello client external object

### Import

To import this module:

- Create a module named `Trello`
- Set the settings as either:
	- A simple URL reference: `https://www.simplicite.io/resources/modules/trello-examples-4.0.xml` (default)
	- A reference to the GitHub project: `{ "type": "git", "origin": { "uri": "https://github.com/simplicitesoftware/module-trello.git" } }`

- Click on the _Import module_ button

### Configure

There are 2 system parameters to configure:

- The `TRELLO_CREDENTIALS` in which you must set your Trello API key, secret and token
- The `TRELLO_CARDEX_SETTINGS` used by the above example business object in which you must set the **Board** ID and its default **List** ID that you want to interact with

> **Note**: it is possible to ovveride these default parameters per user using corresponding user parameters.

`TrelloCardExample` business object definition
----------------------------------------------

Trello example object 1

### Fields

| Name                                                         | Type                                     | Required | Updatable | Personal | Description                                                                      | 
| ------------------------------------------------------------ | ---------------------------------------- | -------- | --------- | -------- | -------------------------------------------------------------------------------- |
| `trelloCardExName`                                           | char(100)                                | yes*     | yes       |          | Card name                                                                        |
| `trelloCardExDescription`                                    | text(1000000)                            |          | yes       |          | Card description                                                                 |
| `trelloCardExCardId`                                         | char(50)                                 |          |           |          | Card ID                                                                          |

### Custom actions

No custom action

`TrelloAPIClientExample` external object definition
---------------------------------------------------

Trello API client example


`TrelloWebhook` external object definition
------------------------------------------

For this webhook to be registred by Trello, you need to make an API call:

```
curl -X POST -H "Content-Type: application/json" https://api.trello.com/1/tokens/<token>/webhooks/ -d '{
  "key": "<key>",
  "callbackURL": "https://<base URL>/ext/TrelloWebhook",
  "idModel":"<board ID>",
  "description": "Demo webhook"
}'
```

To list existing webhooks:

```curl
curl -s https://api.trello.com/1/tokens/<token>/webhooks?key=<key>
```

**NOTE**: This is done **automatically** by the `TrelloCardExample` business object.



