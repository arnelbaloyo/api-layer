/*
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-v20.html
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Copyright Contributors to the Zowe Project.
 */

package com.ca.mfaas;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
public class Controllers {
    @RequestMapping(value = "/apidoc", produces = "application/json")
    public Object apiDoc() {
        return "{\n" +
            "  \"swagger\": \"2.0\",\n" +
            "  \"info\": {\n" +
            "    \"version\": \"1.0.0\",\n" +
            "    \"title\": \"Swagger Petstore\",\n" +
            "    \"license\": {\n" +
            "      \"name\": \"MIT\"\n" +
            "    }\n" +
            "  },\n" +
            "  \"host\": \"petstore.swagger.io\",\n" +
            "  \"basePath\": \"/v1\",\n" +
            "  \"schemes\": [\n" +
            "    \"http\"\n" +
            "  ],\n" +
            "  \"consumes\": [\n" +
            "    \"application/json\"\n" +
            "  ],\n" +
            "  \"produces\": [\n" +
            "    \"application/json\"\n" +
            "  ],\n" +
            "  \"paths\": {\n" +
            "    \"/pets\": {\n" +
            "      \"get\": {\n" +
            "        \"summary\": \"List all pets\",\n" +
            "        \"operationId\": \"listPets\",\n" +
            "        \"tags\": [\n" +
            "          \"pets\"\n" +
            "        ],\n" +
            "        \"parameters\": [\n" +
            "          {\n" +
            "            \"name\": \"limit\",\n" +
            "            \"in\": \"query\",\n" +
            "            \"description\": \"How many items to return at one time (max 100)\",\n" +
            "            \"required\": false,\n" +
            "            \"type\": \"integer\",\n" +
            "            \"format\": \"int32\"\n" +
            "          }\n" +
            "        ],\n" +
            "        \"responses\": {\n" +
            "          \"200\": {\n" +
            "            \"description\": \"An paged array of pets\",\n" +
            "            \"headers\": {\n" +
            "              \"x-next\": {\n" +
            "                \"type\": \"string\",\n" +
            "                \"description\": \"A link to the next page of responses\"\n" +
            "              }\n" +
            "            },\n" +
            "            \"schema\": {\n" +
            "              \"$ref\": \"#/definitions/Pets\"\n" +
            "            }\n" +
            "          },\n" +
            "          \"default\": {\n" +
            "            \"description\": \"unexpected error\",\n" +
            "            \"schema\": {\n" +
            "              \"$ref\": \"#/definitions/Error\"\n" +
            "            }\n" +
            "          }\n" +
            "        }\n" +
            "      },\n" +
            "      \"post\": {\n" +
            "        \"summary\": \"Create a pet\",\n" +
            "        \"operationId\": \"createPets\",\n" +
            "        \"tags\": [\n" +
            "          \"pets\"\n" +
            "        ],\n" +
            "        \"responses\": {\n" +
            "          \"201\": {\n" +
            "            \"description\": \"Null response\"\n" +
            "          },\n" +
            "          \"default\": {\n" +
            "            \"description\": \"unexpected error\",\n" +
            "            \"schema\": {\n" +
            "              \"$ref\": \"#/definitions/Error\"\n" +
            "            }\n" +
            "          }\n" +
            "        }\n" +
            "      }\n" +
            "    },\n" +
            "    \"/pets/{petId}\": {\n" +
            "      \"get\": {\n" +
            "        \"summary\": \"Info for a specific pet\",\n" +
            "        \"operationId\": \"showPetById\",\n" +
            "        \"tags\": [\n" +
            "          \"pets\"\n" +
            "        ],\n" +
            "        \"parameters\": [\n" +
            "          {\n" +
            "            \"name\": \"petId\",\n" +
            "            \"in\": \"path\",\n" +
            "            \"required\": true,\n" +
            "            \"description\": \"The id of the pet to retrieve\",\n" +
            "            \"type\": \"string\"\n" +
            "          }\n" +
            "        ],\n" +
            "        \"responses\": {\n" +
            "          \"200\": {\n" +
            "            \"description\": \"Expected response to a valid request\",\n" +
            "            \"schema\": {\n" +
            "              \"$ref\": \"#/definitions/Pets\"\n" +
            "            }\n" +
            "          },\n" +
            "          \"default\": {\n" +
            "            \"description\": \"unexpected error\",\n" +
            "            \"schema\": {\n" +
            "              \"$ref\": \"#/definitions/Error\"\n" +
            "            }\n" +
            "          }\n" +
            "        }\n" +
            "      }\n" +
            "    }\n" +
            "  },\n" +
            "  \"definitions\": {\n" +
            "    \"Pet\": {\n" +
            "      \"required\": [\n" +
            "        \"id\",\n" +
            "        \"name\"\n" +
            "      ],\n" +
            "      \"properties\": {\n" +
            "        \"id\": {\n" +
            "          \"type\": \"integer\",\n" +
            "          \"format\": \"int64\"\n" +
            "        },\n" +
            "        \"name\": {\n" +
            "          \"type\": \"string\"\n" +
            "        },\n" +
            "        \"tag\": {\n" +
            "          \"type\": \"string\"\n" +
            "        }\n" +
            "      }\n" +
            "    },\n" +
            "    \"Pets\": {\n" +
            "      \"type\": \"array\",\n" +
            "      \"items\": {\n" +
            "        \"$ref\": \"#/definitions/Pet\"\n" +
            "      }\n" +
            "    },\n" +
            "    \"Error\": {\n" +
            "      \"required\": [\n" +
            "        \"code\",\n" +
            "        \"message\"\n" +
            "      ],\n" +
            "      \"properties\": {\n" +
            "        \"code\": {\n" +
            "          \"type\": \"integer\",\n" +
            "          \"format\": \"int32\"\n" +
            "        },\n" +
            "        \"message\": {\n" +
            "          \"type\": \"string\"\n" +
            "        }\n" +
            "      }\n" +
            "    }\n" +
            "  }\n" +
            "}";
    }
}
