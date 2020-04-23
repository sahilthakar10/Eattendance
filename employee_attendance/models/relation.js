const Attendance = require("../models/attendance");
const Employee = require("../models/employee");
const Company = require("../models/company");

Employee.hasMany(Attendance, {
    constraints: true,
    foreignKey : 'employeeCode',
    sourceKey: 'employeeCode',
    onDelete: "CASCADE"
});

Attendance.belongsTo(Employee, {
    constraints: true,
    targetKey: 'employeeCode',
    foreignKey : 'employeeCode',
    onDelete: "CASCADE"
});

