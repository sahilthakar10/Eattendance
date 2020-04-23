const Employee = require('./../models/employee')
const Attendance = require('./../models/attendance')

exports.getAttendanceList = async(req , res)=>{

    let data = await Employee.findAll({

        include : [{
            model :Attendance,
            required:false
        }]
    })

    res.json({statusCode:200 ,data})
}
