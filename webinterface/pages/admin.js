import Head from 'next/head'
import Admin from '../components/admin'
import Card from '../components/card'
import Footer from '../components/footer'
import Heading from '../components/heading'
import Navbar from '../components/navbar'
import NoSSR from '../components/no_ssr'
import { root_style } from '../js/style'

export default function Admin_() {
	return (
		<NoSSR>
			<div className="container" style={root_style}>
				<Head>
					<title>Admin</title>
					<link rel="icon" href="/favicon.ico" />
					<link rel="stylesheet" href="/style.css" />
				</Head>

				<Navbar title="Admin" />

				<Heading title="BetterNudel" subtitle="Admin site!"/>



				<Card>
					<Admin />
				</Card>

				<Footer />
			</div>
		</NoSSR>
	)
}
