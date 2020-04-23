const Router = require('express').Router()

Router.use('/company' ,require('./company'))
Router.use('/attendance' ,require('./attendance'))
Router.use('/employee' ,require('./employee'))

module.exports = Router;