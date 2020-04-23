const express = require('express')
const Router = require('./routes/router.js')
const app = express()
const dotenv = require("dotenv");
const sequelize = require("./helpers/database");
const bodyParser = require('body-parser') 

dotenv.config();
app.use(bodyParser.urlencoded({ extended: false }));
app.use(bodyParser.json());
app.use('/' , Router)


require("./models/Relation");
sequelize
    .sync({
        alter:false
    })
    .then(() => {
        app.listen(8800, () => {
            console.log("Server is Running!");
        });
    })
    .catch(err => {
        console.error(`Sequelize Error : ${err}`);
    });
