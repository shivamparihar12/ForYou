const { Schema } = require("mongoose")
const mongoose=require("mongoose")
const schema=mongoose.schema

const arrayOfPairSchema=new Schema({
    array: Object,
    user_id: String,
    title:String,
    date:String
})

const arrayOfPair=mongoose.model('arrayOfPair', arrayOfPairSchema)
module.exports=arrayOfPair