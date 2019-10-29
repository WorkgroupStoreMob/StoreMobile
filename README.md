# SMob

## Table of Contents
1. [Overview](#Overview)
1. [Product Spec](#Product-Spec)
1. [Wireframes](#Wireframes)

## Overview
### Description
Allow user to create store, view list of stores existed, sale and buy stuffs.

### App Evaluation
- **Category:** Business
- **Mobile:** This app would be primarily developed for mobile but would perhaps be just as viable on a computer, such as tinder or other similar apps. Functionality wouldn't be limited to mobile devices, however mobile version could potentially have more features.
- **Story:** present products online for promotion with the ability to sell and buy more easily
- **Market:** Any individual could choose to use this app. 
All traders in the formal and / or informal sector can present their products, which will be available to any category of people on the market
- **Habit:** This app could be used as often or unoften as the user wanted depending on his needs, and what exactly they're looking for.
- **Scope:** First user can zap into the list of shops and see their contents, then login or create a shop.

## Product Spec
### 1. User Stories (Required and Optional)

**Required Must-have Stories**

* User can view a list of store, view Items in a chosen store.
* User can sign up. 
* User can sign in.
* User can add items in basket and save or buy.
* Vendor can create Store.
* Vendor can add items, manage orders and clients.

**Optional Nice-to-have Stories**

 * User can pull down to refresh store timeline and items timeline
 * After login seller can view list of commande and list of client offline
 * User will be using the app Communicate directly with the seller
 * User can choose to receive notification of new items add
 * Seller User can add a Store Logo and description
 * Seller User will have marketing option
 * Create client community
 * share marketing review and video
 * User can view all the marketing video and reviews

### 2. Screen Archetypes

* Login 
* Register - User signs up or logs into their account
* Basket
   * Upon selecting items choice users can save chart for later action or buy items at once.
* List store screen 
   * Allows user to view store listed.
* List items screen
   * Allows user to view items listed, add to chart, save and buy

* Purchase screen
   * Allows user to review items selected, choose payment method.

* Create Store Screen
   * Lets user create Store.
* Store Screen
   * Lets vendor manage items, orders and clients.

* Add items Screen
   * Lets vendor add items to his store.
* Payment notification Screen
   * notify user about bought item


### 3. Navigation

**Tab Navigation** (Tab to Screen)

* Login and/or sign up
* add to chart
* place order

Optional:
* save chart or purchase


**Flow Navigation** (Screen to Screen)
* welcome page -> list items or login page, my store
* Items Selection  -> Login page, Jumps to Chart and/or purchase sheet
* login to store -> open store 


## Wireframes
<img src="storemobile.jpg" width=800><br>

### [BONUS] Digital Wireframes & Mockups
<img src="Smob.png" height=200>

### [BONUS] Interactive
https://www.figma.com/proto/3YTosP5ds8BvoeTIoB56XB/Untitled?node-id=29%3A103&viewport=314%2C384%2C0.5&scaling=scale-down
### Video Walthrough
<img src="walthroughsmob1.gif" height=200>
<img src="walthrough.gif" height=200>


## SCHEMAS

### Models

**Store**


| Property     | Type         | Description |
| --------     | --------     | --------    |
| objectId     | String         | unique id for the store owner to manage his store (default field)  |
| name         | String         | Identifies a store by its name        |
| phone        | String         | Allows anyone to contact the store administration        |
| address       | String          | provides the physical location of a store        |
| password     | String          | Allows a store administrator to manage this store        |
| email        | String          | Allows anyone to contact the store administration       |
| logo_store   | File         | Allows the SMob users to identify a all the stores by their logo        |
| updatedAt   | DateTime         | date when a store is updated (default field)        |
| createdAt   | DateTime         | date when a store is created (default field)       |



**Product**



| Property | Type | Description |
| -------- | -------- | -------- |
| objectId     | String     | unique id for the product to be identified in a store  (default field)     |
| createdAt     | DateTime     | date when a product is created in a store (default field)     |
| updatedAt     | DateTime     | date when a product is updated in a store (default field)     |
| category_product     | String    | Allows to set the category of a product     |
| image_product     | File     | Photo file that represent a specific product    |
| description    | String     | Text     |
| price     | Number     | Buying price of the product in a store at a given time     |
| selling_price     | Number     | Selling price of the product in a store at a given time     |
| qty_product     | Number     | Items quantity in a given category      |


**Order**


| Property | Type | Description |
| -------- | -------- | -------- |
| objectId     | String     | unique id for an order to be identified in a in a transaction  (default field)     |
| product     | Pointer     | Product that is being sold     |
| client     | Pointer     | Client who places the order    |
| createdAt     | DateTime     | date of the transaction    |


### NETWORKING

**List of network requests by screen**







