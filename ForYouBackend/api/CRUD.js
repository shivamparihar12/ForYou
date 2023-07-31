const express = require('express')
const arrayOfPair = require('../models/ArrayOfPair')
const router = express.Router()

router.post('/post-pair', async (req, res) => {
    const {array, id,date,title} = req.body
    if (!array || !id || !date|| !title) {
        return res.status(400).json({success: false, message: 'missing array and/or ID in body'})
    }

    const newArrayOfPair = new arrayOfPair({
        array: array,
        user_id: id,
        title:title,
        date:date
    })

    try {
        await newArrayOfPair.save()
        return res.status(200).json({success: true, message: 'Success'})
    } catch (err) {
        return res.status(400).json({success: false, message: 'Error occured while accessing database'});
    }
})

router.post('/get-arrays', async (req, res) => {
    const {id} = req.body
    if (!id) {
        return res.status(400).json({success: false, message: 'Missing ID'})
    }

    try {
        const arrays = await arrayOfPair.find({user_id: id})
        res.status(200).json({data: arrays})
    } catch (e) {
        console.error(e)
        return res.status(400).json({success: false, message: 'Error occured while accessing database'})
    }
})

module.exports = router;