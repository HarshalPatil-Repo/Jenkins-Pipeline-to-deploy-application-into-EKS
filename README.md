# Jenkins Pipeline to Deploy Application into EKS

In this project I have deployed simple java based application into Kubernetes (EKS) Deployment consist of 6 pods using Jenkins pipeline. Below tools are integrated with this pipeline:
- Git: Application code repository
- Maven: To build code into package and create artifact
- Nexus: Backup repository for release candidate and artifact
- Docker: To build docker image and push to docker hub repository
- Kubernetes (EKS): To deploy application into K8S deployment consist of 6 pods

## Steps
1.Create total 2 EC2 instances of Amazon Linux with t2.medium

2.On 1st server:
- Install jenkins (with java 17)
- Configure jenkins
- Install Git
- Install maven (with java 11)
- Install Docker and add jenkins user in docker group. Also modify permission for docker.sock file. This will allow to run docker command in jenkins shell
- Install Kubectl CLI
- Configure IAM user in Jenkins shell. Same IAM user need to be used to create EKS cluster

3.On 2rd server:
- Install nexus with java 11
- Configure nexus

4.Create EKS Cluster and Node groups with 2 worker nodes

5.On 1st server, switch to jenkins shell and get kubernetes config file using below command
```bash
aws eks update-kubeconfig --region {REGION} --name {EKS_CLUSTER_NAME}
```
7.On Github modify pom.xml for Nexus and Deployment.yaml for K8S as per docker image name

## Jenkins Pipeline
  * ![image](https://github.com/user-attachments/assets/64b2d8bc-7ad9-409b-83b7-0f2c879a022c)

## Nexus Artifact Upload
  * ![image](https://github.com/user-attachments/assets/3459c12d-1dcb-4b5f-b59b-cb5db42d3980)

## Docker Image
  * ![image](https://github.com/user-attachments/assets/14bdb2b6-1969-45bf-a168-e80d5809cde1)
  * ![image](https://github.com/user-attachments/assets/4c8c70b4-ffb1-469e-b2d2-3110d8c6ab5e)

## Kubernetes (EKS) Deployment
  * ![image](https://github.com/user-attachments/assets/dbc03100-92b8-4367-9ee5-4e9991bda5d6)
  * ![image](https://github.com/user-attachments/assets/7a021f76-32bc-4dcd-99be-b2f3c597305e)
  * ![image](https://github.com/user-attachments/assets/8dafabe0-4856-4fd2-bfcb-da0057d948ee)

## Application Pod exposed using NodePort Service
  * ![image](https://github.com/user-attachments/assets/23f53899-b510-4b47-98d0-4ec323d45d5e)









  

