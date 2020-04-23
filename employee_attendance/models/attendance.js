const Sequelize = require("sequelize");

const sequelize = require("../helpers/database");

const Attendance = sequelize.define(
    "attendance",
    {
        _id: {
            type: Sequelize.BIGINT.UNSIGNED,
            autoIncrement: true,
            allowNull: false,
            primaryKey: true
        },attend: {
            type: Sequelize.STRING(128),
            allowNull: true
        },date: {
            type: Sequelize.DATEONLY, 
            defaultValue: Sequelize.NOW 
        }
       
    }, {
        updatedAt: false
    }
);

module.exports = Attendance;
