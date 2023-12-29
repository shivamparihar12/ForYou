# ForYou
ForYou is an Android application to get real-time analysis of the emotions of the person you are meeting with on an online platform such as Google Meets or Zoom.
This app uses Mediaprojection to cast the screen to SurfaceView, from which we feed the data to the ImageReader. 
Using ImageReader, you get frames on which, using the emotion analysis model, we get emotion attributes.

I have used this publically available already trained model https://www.kaggle.com/datasets/pramod722445/model300

I have Deployed the AI model using TensorFlowlite on this Android Application.
I am using Nodejs and MongoDB for the backend.

We save these emotion attributes to the user's database on our backend. The user has access to this database that he can use to analyze the whole meeting.

<p>Saved user data file and  Data Visualization of the saved file 

<p><img src="https://github.com/shivamparihar12/ForYou/blob/master/images/saved%20user%20files.jpeg" width="250" height="550" />
<img src="https://github.com/shivamparihar12/ForYou/blob/master/images/data%20visualisation.jpeg" width="250" height="550" />
<img src="https://github.com/shivamparihar12/ForYou/blob/master/images/data%20visualization.gif" width="250" height="550" />

# Other Functionalities
Register Page: <br>

<img src="https://github.com/shivamparihar12/ForYou/blob/master/images/register.gif" width="250" height="550" />

Login Page: <br>

<img src="https://github.com/shivamparihar12/ForYou/blob/master/images/signin.gif" width="250" height="550" />

Swipe functionality to swipe between login and register option or vice versa: <br>

<img src="https://github.com/shivamparihar12/ForYou/blob/master/images/swipe%20func%20to%20switch%20between%20sign%20and%20register%20page.gif" width="250" height="550" />



