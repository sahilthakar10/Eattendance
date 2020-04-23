const Employee = require('./../models/employee')
const Attendance = require('./../models/attendance')
const bcrypt = require("bcryptjs");
const { ErrorHandler } = require("../helpers/error");
const Op = require("sequelize").Op;
exports.checkin = async(req , res)=>{

   try {
    let eCode = req.body.eCode;

    let employee = await Employee.findOne({
        where : {
            employeeCode : eCode,
        }
    })

    if (!employee) {
        throw new ErrorHandler(404, "Employee Code Not Found");
    }


    if (req.body.fingerPrint == 0) {
        let password = req.body.password;
        
        let epassword =await bcrypt.hashSync(password, 10)

        let passwordValid = bcrypt.compareSync(
            password,
            employee.password
        );
    
        if (!passwordValid) {
            throw new ErrorHandler(422,  "Please check Password again");
        }
    }

    let attendance = await Attendance.create({
        attend : "CheckIn",
        employeeCode : eCode,
    });

  

        res.json({statusCode:200 ,message:"Successfull Check In"})

   } catch (error) {
         res.json(error)
   }
        
}

exports.checkout = async(req , res)=>{

    try {
     
        let eCode = req.body.eCode;

        console.log("Ecode" + eCode);
        

        let employee = await Employee.findOne({
            where : {
                employeeCode : eCode,
            }
        })
    
        if (!employee) {
            throw new ErrorHandler(404, "Employee Code Not Found");
        }
    
        if (!req.body.fingerPrint) {
            let password = req.body.password;
            let epassword =await bcrypt.hashSync(password, 10)
        
            let passwordValid = bcrypt.compareSync(
                password,
                employee.password
            );
         
            if (!passwordValid) {
                throw new ErrorHandler(422,  "Please check Password again");
            }
        }

        let attendance = await Attendance.create({
            attend : "CheckOut",
            employeeCode : eCode,
        });

         res.json({statusCode:200 , message:"Successfull Check Out"})
        
    } catch (error) {
        res.json(error)
    }

}

exports.register = async(req , res)=>{

    try {
        let name = req.body.fullName;
        let mobileNo = req.body.mobileNo;
        let dob = req.body.dob;
        let eCode = req.body.eCode;
        let password = req.body.password;
        
        let epassword = await bcrypt.hashSync(password, 10);
        
        let employee = await Employee.findOne({
            where : {
                [Op.or]:{
                    employeeCode : eCode,
                    mobile : mobileNo    
                }
            }
        })
       
        if (employee) {
            return res.json({
                statusCode:422,
                message: "Employee already Exist!"
            });
        }

        let createEmployee = await Employee.create({
            fullName : name,
            mobile : mobileNo,
            DOB : dob,
            employeeCode : eCode,
            password : epassword
        });
       
        if (!createEmployee) {
            throw new ErrorHandler(422, "Please Try again",
             );
        }

        res.json({statusCode:200 ,message:"Register Successfull"})
    } catch (error) {
        res.json(error)
    }
}

exports.editEmployee = async(req ,res)=>{

    try {
        let name = req.body.fullName;
        let mobileNo = req.body.mobileNo;
        let eCode = req.body.eCode;
    
    
        let employee = await Employee.findOne({
            where : {
                employeeCode : eCode,
            }
        })
    
       
        if (!employee) {
            return res.json({
                statusCode:404,
                message: "Employee Code is not Exist! Please Do Register!"
            });
        }
    
        const editEmp = await Employee.update(
            {
                fullName : name,
                mobile : mobileNo
            },
            {
                where: {
                    employeeCode: eCode
                }
            }
        );
        if (!editEmp) {
            throw new ErrorHandler(422,"Please Try again",
             );
        }
    
        res.json({statusCode:200 ,message:"Update Successfull"})
    } catch (error) {
        res.json(error)
    }


}

exports.deleteEmp = async(req ,res)=>{

    try {
        let eCode = req.params.eCode;

        let employee = await Employee.findOne({
            where : {
                employeeCode : eCode
            }
        })
    
        if (!employee) {
            throw new ErrorHandler(404, "Employee Not Exist!");
        }
    
        await employee.destroy();

        res.json({statusCode:200 ,message:"Deleted Successfull"})
    } catch (error) {
            res.json(error)
    }


 
}