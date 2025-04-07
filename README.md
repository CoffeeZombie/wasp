# TODO

- Fix ModelMapper
- JSON Binary Type
- Relationship Mapping

# How to use

1. Run the application
2. Post config JSON to http://localhost:8080/generator

## Sample JSON

```json
{
  "projectLocation": "/home/vincent/Dev/wasp-test",
  "packageName": "dev.coffeezombie.wasp_test",
  "overwriteFiles": true,
  "classes": {
    "entity": true,
    "dto": true,
    "repository": true,
    "service": true,
    "controller": true,
    "tests": true
  },
  "defaultPreferences": {
    "jsonIgnoreUnknown": true,
    "modelMapper": true
  },
  "entities": [
    {
      "name": "Person",
      "idName": "id",
      "properties": [
        { "name": "id", "type": "Long" },
        { "name": "myLong", "type": "Long" },
        { "name": "myBd", "type": "BigDecimal" },
        { "name": "myBi", "type": "BigInteger" },
        { "name": "myBool", "type": "boolean" },
        { "name": "myPBool", "type": "Boolean" },
        { "name": "myPDouble", "type": "Double" },
        { "name": "myDouble", "type": "double" },
        { "name": "myFloat", "type": "float" },
        { "name": "myInt", "type": "int" },
        { "name": "myInteger", "type": "Integer" },
        { "name": "myPLong", "type": "long" },
        { "name": "myString", "type": "String" },
        { "name": "myDate", "type": "Date" },
        { "name": "myJson", "type": "JsonBinaryType" }
      ]
    },
    {
      "name": "PersonThing",
      "idName": "id",
      "properties": [
        { "name": "id", "type": "Long" },
        { "name": "myLong", "type": "Long" },
        { "name": "myBd", "type": "BigDecimal" }
      ]
    }
  ]
}
```