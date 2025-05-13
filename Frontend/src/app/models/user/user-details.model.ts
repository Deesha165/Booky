import { UserRole } from "../../enums/user-role.model";

export interface UserDetails{
    id:Number,
    name:String,
    email:String,
    isActive:Boolean,
    role:UserRole
}
