Pluris - Lightweight framework to handle plurals in java applications
=====================================================================

Install:
--------
Clone pluris project and install in your local repository:

	$ mvn clean install

Use it like a maven dependency on your project

	<dependency>
		<groupId>br.com.six2six</groupId>
		<artifactId>pluris</artifactId>
		<version>1.0</version>
	</dependency>

Usage:
------
Add a pluris.json file in the root of your project classpath;

Add messages to the file following the format below:

	{
	   "config":{
	      "messages":[
	         {
	            "locale":"en",
	            "messageKey":"fruits.message",
	            "messagePattern":"{0} and {1}",
	            "plurals":[
	               {
	                  "zeroOccurs":"{0} apples",
	                  "oneOccur":"{0} apple",
	                  "manyOccurs":"{0} apples"
	               },
	               {
	                  "zeroOccurs":"no pineapple",
	                  "oneOccur":"{1} pineapple",
	                  "manyOccurs":"{1} pineapples"
	               }
	            ]
	         },
	         {
	            "locale":"pt_BR",
	            "messageKey":"fruits.message",
	            "messagePattern":"{0} e {1}",
	            "plurals":[
	               {
	                  "zeroOccurs":"{0} ma��s",
	                  "oneOccur":"{0} ma��",
	                  "manyOccurs":"{0} ma��s"
	               },
	               {
	                  "zeroOccurs":"nenhum abacaxi",
	                  "oneOccur":"{1} abacaxi",
	                  "manyOccurs":"{1} abacaxis"
	               }
	            ]
	         }
	      ]
	   }
	}

You can use a short locale to your messages. Pluris is able to find messages for "en" when using "en_US" as default locale;

Retrieve a message

	String msg = PlurisUtils.getMessage("your.message.key", arg1, ...argN);

Or retrieve a message for a specific locale:

	String msg = PlurisUtils.getMessage(new Locale("pt", "BR"), "your.message.key", arg1, ...argN);

If you want to split your messages into multiple files, add a pluris.properties in the root of your project classpath:

	files.to.load=/file.json, /file_en_US.json, /file_pt_BR.json
