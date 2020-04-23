const Employee = require('./../models/employee')
const Attendance = require('./../models/attendance')
const Company = require('./../models/company')
const bcrypt = require("bcryptjs");
const { ErrorHandler } = require("../helpers/error");

exports.reg = async(req , res)=>{


    
        let companyName = req.body.companyname;
        let user = req.body.Username;
        let password = req.body.password;
        let epassword = await bcrypt.hashSync(password, 10);
    
        let company = await Company.findOne({
            where : {
                username : user,
            }
        })

        if (company) {
            return res.json({statusCode : 400 ,
                message: "Email already Exist! Please try Log In!"
            });
        }
    
        let createCompany = await Company.create({
            companyName : companyName,
            username : user,
            password : epassword
        });
       
        if (!createCompany) {
            throw new ErrorHandler(422,  "Please Try again" );
        }

        res.json({statusCode:200 ,message:"Register Successfull"})
   

}

exports.login = async(req , res , next)=>{

    let user = req.body.Username;
    let password = req.body.password;
    let epassword =await bcrypt.hashSync(password, 10)

  
    try {
        let company = await Company.findOne({
            where : {
                username : user,
            }
        })
    
    
        if (!company) {
            throw new ErrorHandler(404,"Username Not Found" );
        }

        let passwordValid = bcrypt.compareSync(
            password,
            company.password
        );

        if (!passwordValid) {
            throw new ErrorHandler(422, "Please check Password again");
        }
       
        res.json({statusCode:200 ,message:"Login Successfull" })
    } catch (error) {
        res.json(error)
    }
   

}

