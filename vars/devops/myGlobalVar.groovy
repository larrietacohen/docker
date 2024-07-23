def call(){
    return "Hello From Libraries"
}

def greet(){
    return "EDBERPHAYDER EL MEJOR"
}

def slackStarted(BUILD_URL, ENV, ACCOUNT_ID, AWS_REGION, REPO_NAME){
    def iconsSuccess = [":typingcat:", ":batmanrunning:", ":windows:", ":ahhhh:", ":typing_frog:"]
    def randomIndex = (new Random()).nextInt(iconsSuccess.size())
    def attachments = [
        [
        text: "@here *Notification: STARTED | check it here (<${env.BUILD_URL}|Open>)!* ${iconsSuccess[randomIndex]} \n*Pipeline:* ${ENV}-${REPO_NAME} \n*Region:* ${AWS_REGION} \n*Account:* ${ACCOUNT_ID} \n*Env:* ${ENV}",
        color: '#0140c8'
        ]
    ]
    if ("${ENV}" == 'qa' || "${ENV}" == 'qc'){
        slackSend(channel: "#backend-notification-pipeline-low", attachments: attachments)
    }
    if ("${ENV}" == 'stg' || "${ENV}" == 'prod'){
        slackSend(channel: "#backend-notification-pipeline-high", attachments: attachments)
    }
}

def slackSuccess(BUILD_URL, ENV, ACCOUNT_ID, AWS_REGION, REPO_NAME){
    def iconsSuccess = [":everythings_fine_parrot:", ":beer:", ":man_dancing:",":party_parrot:", ":troll_parrot:"]
    def randomIndex = (new Random()).nextInt(iconsSuccess.size())
    def attachments = [
        [
        text: "@here *Notification: SUCCEEDED | check it here (<${env.BUILD_URL}|Open>)!* ${iconsSuccess[randomIndex]} \n*Pipeline:* ${ENV}-${REPO_NAME} \n*Region:* ${AWS_REGION} \n*Account:* ${ACCOUNT_ID} \n*Env:* ${ENV}",
        color: '#00c100'
        ]
    ]
    if ("${ENV}" == 'qa' || "${ENV}" == 'qc'){
        slackSend(channel: "#backend-notification-pipeline-low", attachments: attachments)
        pruneDockerImagesLow()
    }
    if ("${ENV}" == 'stg' || "${ENV}" == 'prod'){ 
        slackSend(channel: "#backend-notification-pipeline-high", attachments: attachments)
        pruneDockerImagesHigh()
    }
}

def slackFailure(BUILD_URL, ENV, ACCOUNT_ID, AWS_REGION, REPO_NAME){
    def iconsFailure = ["::alert::", ":typingcat:", ":crisis:", ":facepalm:", ":duckhuntdog:"]
    def randomIndex = (new Random()).nextInt(iconsFailure.size())
    def attachments = [
        [
        text: "@here *Notification: FAILED | check it here (<${env.BUILD_URL}|Open>)!* ${iconsFailure[randomIndex]} \n*Pipeline:* ${ENV}-${REPO_NAME} \n*Region:* ${AWS_REGION} \n*Account:* ${ACCOUNT_ID} \n*Env:* ${ENV}",
        color: '#ff0000'
        ]
    ]
    if ("${ENV}" == 'qa' || "${ENV}" == 'qc'){
        slackSend(channel: "#backend-notification-pipeline-low", attachments: attachments)
    }
    if ("${ENV}" == 'stg' || "${ENV}" == 'prod'){
        slackSend(channel: "#backend-notification-pipeline-high", attachments: attachments)
    }
}
