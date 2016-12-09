//
//  Page.swift
//  No te limites
//
//  Created by Griselda Epifanio on 12/8/16.
//  Copyright © 2016 Jose Epifanio. All rights reserved.
//

import Foundation

class Page
{
    private var id:String = ""
    private var url:String = ""
    
    //Seters
    public func setId(id: String) {
        self.id = id
    }
    
    public func setUrl(url: String) {
        self.url = url
    }
    
    //Geters
    public func getId()-> String {
        return self.id
    }
    
    public func getUrl()-> String {
        return self.url
    }
}
