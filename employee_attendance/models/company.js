const Sequelize = require("sequelize");

const sequelize = require("../helpers/database");

const Company = sequelize.define(
    "company",
    {
        _id: {
            type: Sequelize.BIGINT.UNSIGNED,
            autoIncrement: true,
            allowNull: false,
            primaryKey: true
        },
        companyName: {
            type: Sequelize.STRING(64),
            allowNull: true
        },
        username: {
            type: Sequelize.STRING(128),
            unique: true,
            allowNull: true
        },
        password: {
            type: Sequelize.STRING(255),
            allowNull: true
        },
    },
    {
        timestamps: false,
    }
);

module.exports = Company;
