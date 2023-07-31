# Applicant Info

NAME - SHIVAM PARIHAR <br>
UNIVERSITY - Indian Institute of Technology, Patna <br>
DEPARTMENT - Electrical and Electronics Engineering


# ForYou
ForYou is an android application to get real-time analysis of the emotions of the person you are in a meeting with on an online meeting platform such as google meet or zoom.
This app uses Mediaprojection to cast the screen to SurfaceView from which we feed the data to the ImageReader. 
Using ImageReader, you get frames on which, using the emotion analysis model, we get emotion attributes.

I have used this publically available already trained model https://www.kaggle.com/datasets/pramod722445/model300

I have Deployed the AI model using TensorFlowlite on Frontend.
I am using Nodejs and MongoDB for the backend.

We save these emotion attributes to the user's database on our backend. The user has access to this database that he can use to analyze the whole meeting.

<p>Saved user data file and  Data Visualization of the saved file 

<p><img src="https://github.com/shivamparihar12/ForYou/blob/main/images/saved%20user%20files.jpeg" width="250" height="550" />
<img src="https://github.com/shivamparihar12/ForYou/blob/main/images/data%20visualisation.jpeg" width="250" height="550" />
<img src="https://github.com/shivamparihar12/ForYou/blob/main/images/data%20visualization.gif" width="250" height="550" />

# Other Functionalities
Register Page: <br>

<img src="https://github.com/shivamparihar12/ForYou/blob/main/images/register.gif" width="250" height="550" />

Login Page: <br>

<img src="https://github.com/shivamparihar12/ForYou/blob/main/images/signin.gif" width="250" height="550" />

swipe functionality to swipe between login and register option or vice versa: <br>

<img src="https://github.com/shivamparihar12/ForYou/blob/main/images/swipe%20func%20to%20switch%20between%20sign%20and%20register%20page.gif" width="250" height="550" />



