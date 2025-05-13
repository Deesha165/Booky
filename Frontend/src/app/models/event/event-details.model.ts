import { CategoryDetails } from "./category-details.model"

export interface EventDetails{
id:Number,
name:String,
description:String,
eventTime:Date,
venue:String,
pricePerTicket:Number,
availableTickets:Number,
image:String,
category:CategoryDetails,
isBooked:Boolean
}
