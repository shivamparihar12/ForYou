const { Schema } = require("mongoose")
const mongoose=require("mongoose")
const schema=mongoose.Schema

const userResponseData= new Schema({
    id:String,
    name:String,
    email:String,
    password:String,
    __v:String
})

const User=mongoose.model('userResponseData',userResponseData)
module.exports=User