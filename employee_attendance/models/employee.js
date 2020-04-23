const Sequelize = require("sequelize");

const sequelize = require("../helpers/database");

const Employee = sequelize.define(
    "employee",
    {
        _id: {
            type: Sequelize.BIGINT.UNSIGNED,
            autoIncrement: true,
            allowNull: false,
            primaryKey: true
        },
        fullName: {
            type: Sequelize.STRING(64),
            allowNull: true
        },
        mobile: {
            type: Sequelize.BIGINT(10).UNSIGNED,
            unique: true,
            allowNull: false
        },
        DOB: {
            type: Sequelize.DATEONLY, 
            defaultValue: Sequelize.NOW 
        },
        employeeCode: {
            type: Sequelize.STRING(128),
            unique: true,
            allowNull: true
        },
        password: {
            type: Sequelize.STRING(255),
            allowNull: true
        },
        fingerPrint: {
            type: Sequelize.BOOLEAN,
            defaultValue: true
        }
    },
    {
        timestamps: false,
       
    }
);

module.exports = Employee;
