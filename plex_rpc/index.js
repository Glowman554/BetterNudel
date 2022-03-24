const axios = require('axios');
const fs = require("fs");

function getRelevantData(session){
    return {
        state: session['Player']['state'],
        library: session['librarySectionTitle'],
        title: session['grandparentTitle'],
        season: session['parentIndex'],
        episode: {
            title: session['title'],
            index: session['index'],
            duration: session['duration'],   // milliseconds
            progress: session['viewOffset']  // milliseconds
        }
    }
}

async function getPlexSession(host, port){
    const resp = await axios.get(`http://${host}:${port}/status/sessions`);
    if(resp['status'] === 200) {
        const data = resp['data'];
        if(data['MediaContainer']['size'] == '0'){
            return null;
        }
        for(const session of data['MediaContainer']['Metadata']){
            return getRelevantData(session);
        }
    }
}

function to_rpc_str(data) {
    return `${data.title} ${data.episode.title ? ("Episode " + data.episode.index + ` (${data.episode.title})`) : "" }`
}

async function request_server_set_rpc(str) {
    const cfg = JSON.parse(fs.readFileSync("cfg.json"));

    console.log(await axios.get(`${cfg.url}/api/v2/plex?event=stream_progress&title=${new Buffer(str).toString('base64')}&token=${cfg.token}`));
}

async function request_server_unset_rpc() {
    const cfg = JSON.parse(fs.readFileSync("cfg.json"));

    console.log(await axios.get(`${cfg.url}/api/v2/plex?event=stream_stop&token=${cfg.token}`));
}

async function main() {

    while (true) {

        try {
            try {
                var rpc = to_rpc_str(await getPlexSession("10.3.141.182", "32400"));
                await request_server_set_rpc(rpc);
            } catch (e) {
                request_server_unset_rpc();
            }
        } catch (e) {}

        await new Promise((res, rej) => setTimeout(res, 1000 * 5));
    }
}

main();