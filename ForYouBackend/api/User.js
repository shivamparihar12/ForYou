const express= require('express')
const router=express.Router()

//mongodb user model
const User=require('./../models/User')
let userResponse=require('./../models/userResponseData')
const bcrypt=require('bcrypt')

//signup
router.post('/signup',(req,res)=>{
    let{name,email,password}=req.body;
    // name=name.trim
    // email=email.trim
    // password=password.trim
    console.log(name)
    console.log(password)
    console.log(email)

    if(name==""|| email=="" || password==""){
        res.json({
            status:"FAILED",
            message:"empty input fields!"
        })
    }
    else if(!/^[a-zA-Z]*$/.test(name)){
        res.json({
            status:"FAILED",
            message:"Invalid name entered"
        })
    }
    else if(!/^[\w-\.]+@([\w-]+\.)+[\w-]{2,4}$/.test(email)){
        res.json({
            status:"FAILED",
            message: "Invaild email"
        })
    }
    else if(password.length<8){
        res.json({
            status:"FAILED",
            message:"Password is too short!"
        })
    }
    else {
        User.find({email}).then(result =>{
            if(result.length){
                res.json({
                    status:"FAILED",
                    messgae:"User already exists with this email"
                })
            }
            else{
                const saltRounds=10;
                bcrypt.hash(password,saltRounds).then(hashedPassword=>{
                    const newUser= new User({
                        name,
                        email,
                        password:hashedPassword,
                    })

                    newUser.save().then(result=>{
                        res.json({
                            // status:"SUCCESS",
                            // messgae:"Signup successful",
                            data:result
                        })
                    })
                    .catch(err=>{
                        res.json({
                            status:"FAILED",
                            messagea:"An error occured while saving account!"
                        })
                    })

                }).catch(err=>{
                    res.json({
                        status:"FAILED",
                        messagea:"An error occured while hashing password!"
                    })
                })

                
            }
        }).catch(err=>{
            console.log(err)
            res.json({
                status:"FAILED",
                message:"An error occured while checking for existing User!" 
            })
        })
    }
})

//signin 
router.post('/signin',(req,res)=>{
    let{email,password}=req.body;

    if(password==""|| email==""){
        res.json({
            status:"FAILED",
            message:"empty input fields!"
        })
    }
    else{
        User.find({email}).then(data=>{
            if(data.length){
                const hashedPassword=data[0].password
                bcrypt.compare(password,hashedPassword).then(result=>{
                    if(result){
                        // res.json({
                            // status:"SUCCESS",
                            // message:"Signin successful",
                            userResponse=data[0]
                            return res.status(200).json({data})
                        // })
                    }
                    else{
                        res.json({
                            status:"FAILED",
                            message:"Invalid Password enter"
                        })
                    }
                })
                .catch(err=>{
                    res.json({
                        status:"FAILED",
                        message:"An error occured while comparing passowrds"
                    })
                })
            }else {
                res.json({
                    status:"FAILED",
                    message:"Invalid credential entered!"
                })
            }
        })
        .catch(err=>{
            res.json({
                status:"FAILED",
                message:"An error occured while checking for passowrds"
            })
        })
    }
})

module.exports=router