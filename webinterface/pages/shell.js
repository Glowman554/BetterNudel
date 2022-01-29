import Head from 'next/head'
import Navbar from '../components/navbar'
import NoSSR from '../components/no_ssr'
import Shell from '../components/shell'
import { root_style } from '../js/style'

export default function Shell_() {

	return (
		<NoSSR>
			<div className="container" style={root_style}>
				<Head>
					<title>Shell</title>
					<link rel="icon" href="/favicon.ico" />
					<link rel="stylesheet" href="/style.css" />
				</Head>

				<Navbar title="Shell" />


				<Shell />
			</div>
		</NoSSR>
	)
}
