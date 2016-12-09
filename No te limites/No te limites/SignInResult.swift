//
//  SignInResult.swift
//  No te limites
//
//  Created by Griselda Epifanio on 12/8/16.
//  Copyright © 2016 Jose Epifanio. All rights reserved.
//

import Foundation

class SignInResult
{
    private var mAuthToken:String = ""
    private var mEmail:String = ""
    
    //Seters
    public func setAuthToken(authToken: String) {
        self.mAuthToken = authToken
    }
    
    public func setEmail(email: String) {
        self.mEmail = email
    }
    
    //Geters
    public func getAuthToken()-> String {
        return self.mAuthToken
    }
    
    public func getEmail()-> String {
        return self.mEmail
    }
    
}
