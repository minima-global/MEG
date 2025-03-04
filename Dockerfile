#Use a JDK that allows multiple platforms
FROM eclipse-temurin:11

#Create a home folder
RUN mkdir -p /home/meg
ENV HOME=/home/meg

#Set the local work directory
ENV LOCAL=/usr/local
WORKDIR $LOCAL

# Get other permissions right, too
RUN mkdir -p $HOME/data

#Install some apps
RUN apt-get update
RUN apt-get -y install vim
RUN apt-get clean
RUN rm -rf /var/lib/apt/lists/*

# Copy the Config
#COPY minima.config $HOME/minima.config

#Copy the minima script
#COPY minima /usr/bin/minima
#RUN chmod +x /usr/bin/minima

#Make BASH the defult
RUN ln -sf /bin/bash /bin/sh

# Copy in startup script, minima and dapps
COPY ./jar/minima-meg-server.jar minima-meg-server.jar

#Move to Home folder
WORKDIR $HOME

# Minima ports
EXPOSE 8888

# Start her up 
#ENTRYPOINT ["java", "-jar", "/usr/local/minima-meg-server.jar", "-conf", "/home/minima/minima.config"]
ENTRYPOINT ["java", "-jar", "/usr/local/minima-meg-server.jar"]
