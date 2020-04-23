const Sequelize = require("sequelize");

const sequelize = new Sequelize(
    "e_attendance",
    "root",
    "",
    {
        dialect: "mysql",
        host: "localhost"
    }
);

module.exports = sequelize;
