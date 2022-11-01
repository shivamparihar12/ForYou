const { Schema } = require("mongoose")
const mongoose=require("mongoose")
const schema=mongoose.Schema

const UserSchema= new Schema({
    name:String,
    email:String,
    password:String
})

const User=mongoose.model('User',UserSchema)
module.exports=User