const EmployeeRouter = require('express').Router()
const employee = require('./../controllers/employee')

EmployeeRouter.post('/checkin' , employee.checkin)
EmployeeRouter.post('/checkout' , employee.checkout)
EmployeeRouter.post('/register' , employee.register)
EmployeeRouter.patch('/edit' , employee.editEmployee)
EmployeeRouter.delete('/delete/:eCode' , employee.deleteEmp)

module.exports = EmployeeRouter