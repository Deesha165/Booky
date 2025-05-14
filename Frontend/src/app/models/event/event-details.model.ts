import { CategoryDetails } from "./category-details.model"

export interface EventDetails{
id:number,
name:String,
description:String,
eventTime:Date,
venue:String,
pricePerTicket:number,
availableTickets:number,
image:String,
isBooked:Boolean
}
