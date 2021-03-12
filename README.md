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

[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=simplicite-modules-Trello&metric=alert_status)](https://sonarcloud.io/dashboard?id=simplicite-modules-Trello)

### Introduction

**Trello** integration examples:

- A business object that synchronizes with Trello cards (using a webhook for incoming updates from Trello)
- A Trello client external object

### Import

To import this module:

- Create a module named `Trello`
- Set the settings as:

```json
{
	"type": "git",
	"origin": {
		"uri": "https://github.com/simplicitesoftware/module-trello.git"
	}
}
```

- Click on the _Import module_ button

### Configure

There is 1 system parameters to configure:

- The `TRELLO_SERVICE` in which you must set your Trello API key, secret, token, the target **Board** ID and its default **List** ID that you want to interact with

> **Note**: it is possible to ovveride these default parameters per user using corresponding user parameters.

`TrelloCardExample` business object definition
----------------------------------------------

Trello card example:

- On record creation it creates a Trello card and stores its ID
- On record update it updates the Trello card corresponding to the stored ID
- On record deletion it deletes the Trello card corresponding to the stored ID

This object uses the settings stored in the `TRELLO_CARDEX_SETTINGS` system parameter.

**NOTE**: this object registers (creates or updates) a Trello webhook when loading

### Fields

| Name                                                         | Type                                     | Required | Updatable | Personal | Description                                                                      |
|--------------------------------------------------------------|------------------------------------------|----------|-----------|----------|----------------------------------------------------------------------------------|
| `trelloCardExName`                                           | char(100)                                | yes*     | yes       |          | Card name                                                                        |
| `trelloCardExDescription`                                    | text(1000000)                            |          | yes       |          | Card description                                                                 |
| `trelloCardExCardId`                                         | char(50)                                 |          |           |          | Card ID                                                                          |

`TrelloClientExample` external object definition
------------------------------------------------

Trello client example:

- From a given board ID it retreives the board's lists
- For each lists it retreives the list's cards


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


