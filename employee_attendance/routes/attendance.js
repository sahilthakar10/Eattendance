const AttendanceRouter = require('express').Router()
const attendance = require('./../controllers/attendance')

AttendanceRouter.get('/getAttendance' , attendance.getAttendanceList)

module.exports = AttendanceRouter