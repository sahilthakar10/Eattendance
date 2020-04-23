const CompanyRouter = require('express').Router()
const company = require('./../controllers/company')

CompanyRouter.post('/login' , company.login)
CompanyRouter.post('/reg' , company.reg)

module.exports = CompanyRouter;