export interface EventCreationRequest{
 
   
    name:String,
    description:String,
    eventTime:Date,
    venue:String,
    pricePerTicket:Number,
    availableTickets:Number,
    image:String,
    categoryId:Number
}