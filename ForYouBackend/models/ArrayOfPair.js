const { json } = require("express")
const { Schema } = require("mongoose")
const mongoose=require("mongoose")
const schema=mongoose.schema

const arrayOfPairSchema=new Schema({
    array:Object
})

const arrayOfPair=mongoose.model('arryaOfPair',arrayOfPairSchema)
module.exports=arrayOfPair